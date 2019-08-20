package com.spc.other.rpcAnetty.simulateDubbo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.vo.ResultVO;
import com.spc.other.rpcAnetty.NettyRPC;
import com.spc.other.rpcAnetty.ServiceImpl;
import com.spc.other.rpcAnetty.ServiceMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 接受的json串格式为：{threadID=21,serviceKey=sayHello,params={name=Chad,...}}
 * 返回的json串格式为：{threadID=21,serviceKey=sayHello,params={name=Chad,...},result=ResultVO}
 * @author Wen, Changying
 * 2019年8月20日
 */
public class DubboServiceProvider {

	public static void main(String[] args) throws Exception {
		doProvide();
	}

	public static void doProvide() throws Exception {
		ServiceImpl si = new ServiceImpl();
		new DubboServiceProvider().provide(si, 8000);
	}
	public void provide(final Object service, int port) throws Exception {
		if(service == null) 
			throw new IllegalArgumentException("service instance == null");
		if(port <=0 || port > 65536) 
			throw new IllegalArgumentException("invalid port "+port);
		System.out.println("service "+service.getClass().getName()+" provided on port "+port);
		EventLoopGroup listener = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(listener,worker);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 128);
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// outBound
					// StringEncoder 将String转为ByteBuf
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
						
					});
					
					// inBound
					// StringDecoder 将ByteBuf转为String
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
					    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
							ResultVO result = null; JSONObject obj = null;
							try {
						        System.out.println("input: "+msg.toString());
						        obj = (JSONObject) JSONObject.parse(msg.toString());
								String methodName = String.valueOf(obj.get("serviceKey"));
								Map<String, Object> params = (Map<String, Object>) obj.get("params");
								Method m = ServiceMap.serviceMap.get(methodName);
								Parameter[] paramName = m.getParameters();
								Class<?>[] paramClass = m.getParameterTypes();
								Object[] param = new Object[paramName.length];
								for(int i = 0; i < paramName.length; i++) {
									Object paramObject = params.get(paramName[i].getName());
									if(paramObject == null) {
										//这里抛异常，直接返回失败信息。
									}
									param[i] = paramObject;
								}
								result = ResultVOUtil.success(m.invoke(new ServiceImpl(), param));
							}catch(Exception e) {
								result = ResultVOUtil.error(1, e.getMessage());
							}
							obj.put("result", result);
					        ctx.writeAndFlush(obj.toString());
					    }
					});
				}
			});
			ChannelFuture f = b.bind(port).sync();
			f.channel().closeFuture().sync();
			
		}finally {
			listener.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}
}
