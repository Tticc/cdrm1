package com.spc.other.rpcAnetty.simulateDubbo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSONObject;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.vo.ResultVO;
import com.spc.other.rpcAnetty.simulateDubbo.MyFutureTask;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DubboService {
	public static void main(String[] args) {
		DubboService ds = DubboService.getInstance();
		JSONObject params = new JSONObject();
		params.put("name", "Tticc");
		ResultVO result = (ResultVO) ds.getDubboService("sayHello", params);
		
	}
	private EventLoopGroup worker = new NioEventLoopGroup();
	private Channel ch;
	private MyFutureTask<Object> mft = new MyFutureTask<Object>();;
	private final String HOST = "127.0.0.1";
	private final int PORT = 8000;
	private ExecutorService es = Executors.newFixedThreadPool(1);
	
	// 用来保存正在等待返回数据的线程
	//private static Map<Long, Thread> waitThreads = new ConcurrentHashMap<Long, Thread>();
	
	/**
	 * 初始化实例时，使用线程池初始化client。<br/>
	 * <p>有一个问题是，如果client的线程因为某些异常挂了，那么该如何恢复呢？也就是如何监控当前线程是否在执行任务？
	 * 如果不能监控，那么只能将许多个初始化任务添加到线程池等待队列中。
	 * 这样，一旦久的线程死去，线程池会创建新线程，重新拿到任务进行client初始化。
	 * </p>
	 */
	private DubboService() {
		es.execute(() -> initClient());
	}
	public static final DubboService getInstance() {
		return SingletonCreater.INSTANCE;
	}
	/**
	 * 使用内部静态类实现 线程安全的 单例模式。<br/>
	 * 这是使用JVM本身机制保证了线程安全。
	 * jvm在加载类时只会加载一次，不管有多少个线程同时进入这里，内部静态类都只会加载一次，也就只有一个instance
	 * <ol>
	 * <li>调用外部类的静态变量和静态方法可以让外部类被加载到内存中，不过被调用的外部类的内部类（不论是否静态）不会被加载。</li>
	 * <li>加载静态内部类之前会先加载外部类，静态内部类或非静态内部类在使用它们的成员时才加载。</li>
	 * <li>内部类可以随意使用外部类的成员对象和方法（即使私有），而不用生成外部类对象</li>
	 * </ol>
	 * @author Wen, Changying
	 * 2019年8月17日
	 */
	private static class SingletonCreater{
		private static final DubboService INSTANCE = new DubboService();
	}
	
	public Object getDubboService(String serviceKey,JSONObject params){
		String errMsg;
		try {
			long threadID = Thread.currentThread().getId();
			mft.putMySelf();
			JSONObject requestJson = new JSONObject();
			requestJson.put("threadID", String.valueOf(threadID));
			requestJson.put("serviceKey", serviceKey);
			requestJson.put("params", params);
			// {thread=21,serviceKey=sayHello,params={name=Chad,...}}
			this.ch.writeAndFlush(requestJson.toString());
			return ResultVOUtil.success(mft.myGet(60,TimeUnit.SECONDS));
		}catch(TimeoutException te) {
			te.printStackTrace();
			errMsg = te.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			errMsg = e.getMessage();
		}
		return ResultVOUtil.error(1, errMsg);
	}

	private void initClient() {
		try {
			Bootstrap b = new Bootstrap();
			b.group(this.worker);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) {
							System.out.println("result: "+msg.toString());
							JSONObject result = (JSONObject) JSONObject.parse(msg.toString());
							long threadID = result.getLongValue("threadID");
							mft.set(threadID, msg);
						}
						@Override
						public void channelActive(ChannelHandlerContext ctx) {
							//when client connect to server, say hello.
							//ctx.writeAndFlush(params.toString());
						}
					});
				}
			}); 
			this.ch = b.connect(HOST, PORT).sync().channel();
			this.ch.writeAndFlush("\r\n");
			// now send message to server, take Thread ID beyond basic info
		}catch (InterruptedException e) {
				e.printStackTrace();
		}finally {
			this.worker.shutdownGracefully();
		}
	}
}
