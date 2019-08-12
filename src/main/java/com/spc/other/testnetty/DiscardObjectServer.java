package com.spc.other.testnetty;

import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
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
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class DiscardObjectServer {
	private int port;
	public DiscardObjectServer() {
		this(8000);
	}
	public DiscardObjectServer(int port) {
		this.port = port;
	}
	public static void main(String[] args) throws Exception{
		new DiscardObjectServer().run();
	}
	private void run() throws Exception{
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
					// ObjectEncoder 将Java Object转为ByteBuf
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
						
					});
					
					// inBound
					// ObjectDecoder 将ByteBuf转为Java Object
					ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                            ClassResolvers.cacheDisabled(null)));
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
					    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
					        System.out.println("input: "+msg.toString());
							ctx.writeAndFlush(msg);
					    }
					});
				}
			});
			ChannelFuture f = b.bind(this.port).sync();
			f.channel().closeFuture().sync();
			
		}finally {
			listener.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

}
