package com.spc.other.rpcAnetty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.cdrm1.vo.ResultVO;

import io.netty.bootstrap.Bootstrap;
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
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

@SuppressWarnings("unchecked")
public class NettyRPC {
	public static void main(String[] args) throws Exception {
		ServiceImpl si = new ServiceImpl();
		new NettyRPC().provide(si, 8000);
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
					        System.out.println("input: "+msg.toString());
					        JSONObject obj = (JSONObject) JSONObject.parse(msg.toString());
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
							Object result = m.invoke(new ServiceImpl(), param);
					        ctx.writeAndFlush(JSONObject.toJSON(ResultVOUtil.success(result)).toString());
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
	
	public Object call(String serviceKey,JSONObject params) throws Exception {
		System.out.println("call service "+serviceKey);
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(worker);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) {
							System.out.println("result: "+msg.toString());
						}
						@Override
						public void channelActive(ChannelHandlerContext ctx) {
							ctx.writeAndFlush(params.toString());
						}
					});
				}
			});
			ChannelFuture cf = b.connect("127.0.0.1",8000);
			cf.channel().closeFuture().sync();
			return cf.get();
		}finally {
			worker.shutdownGracefully();
		}
		
	}
}
