package com.spc.other.testnetty;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.spc.cdrm1.util.ResultVOUtil;
import com.spc.other.rpcAnetty.MyFutureTask;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class DiscardObjectClient {

	public static void main(String[] args) throws Exception {
		//DiscardObjectClient dc = new DiscardObjectClient();
		//dc.connect("127.0.0.1", 8000);
		MyFutureTask<String> f = new MyFutureTask<String>();
		f.set("result");
		System.out.println(f.get());
		
	}
	public void connect(String host, int port, FutureTask<String> f) throws Exception {
		EventLoopGroup worker = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(worker);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ObjectEncoder());
					ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE,
                            ClassResolvers.cacheDisabled(null)));
					ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
						@Override
						public void channelRead(ChannelHandlerContext ctx, Object msg) {
							System.out.println("result: "+msg.toString());
						}
						@Override
						public void channelActive(ChannelHandlerContext ctx) {
							ctx.writeAndFlush(ResultVOUtil.success());
						}
					});
				}
			});
			ChannelFuture cf = b.connect(host,port);
			cf.channel().closeFuture().sync();
		}finally {
			worker.shutdownGracefully();
		}
	}
}
