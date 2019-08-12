package com.spc.other.testnetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class DiscardClient {

	public static void main(String[] args) throws InterruptedException {
		DiscardClient dc = new DiscardClient();
		dc.connect("127.0.0.1", 8000);
	}
	public void connect(String host, int port) throws InterruptedException {
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(workerGroup);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) {
							ByteBuf result = (ByteBuf) msg;
							byte[] result1 = new byte[result.readableBytes()];
							result.readBytes(result1);
							result.release();
							ctx.close();
							System.out.println("server said:"+new String(result1));
						}
						@Override
						public void channelActive(ChannelHandlerContext ctx) {
							String msg = "are you ok?";
							ByteBuf encoded = ctx.alloc().buffer(4*msg.length());
							encoded.writeBytes(msg.getBytes());
							ctx.write(encoded);
							ctx.flush();
						}
					});
				}
			});
			ChannelFuture cf = b.connect(host,port);
			cf.channel().closeFuture().sync();
		}finally {
			workerGroup.shutdownGracefully();
		}
	}
}
