package com.spc.other.rpcAnetty.simulateDubbo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.alibaba.fastjson.JSONObject;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TestContinueWrite {
	private static final String HOST = "192.168.1.103";
	private static final int PORT = 8000;
	public static void main(String[] args) throws Exception{

		// 初始化client。
		DubboService ds = DubboService.getInstance();
		Thread.sleep(3000);
		
		// 调用 dubbo 服务
		
		JSONObject params = new JSONObject();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("0000000000000000000000000000000");
				params.put("name", "Tticccccc");
				ds.getDubboService("sayHello", params);
			}
		}, "Tticc").start();
		//doCall();
	}
	public static void doCall() throws Exception{
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new StringEncoder());
					ch.pipeline().addLast(new StringDecoder());
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) {
							System.out.println("**************//////////output: "+msg.toString());
						}
					});
					
				}
				
			});
			Channel ch = b.connect(HOST, PORT).sync().channel();
			ChannelFuture lastWriteFuture = null;
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			for (;;) {
				String line = in.readLine();
				if (line == null) {
					break;
				}
				lastWriteFuture = ch.writeAndFlush(line + "\r\n");
				if ("bye".equals(line.toLowerCase())) {
					 ch.closeFuture().sync();
					 break;
				}
				if (lastWriteFuture != null) {
					lastWriteFuture.sync();
				}
			}
		}finally {
			// The connection is closed automatically on shutdown.
			group.shutdownGracefully();
		}
	}
	
}
