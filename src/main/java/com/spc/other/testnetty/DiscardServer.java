package com.spc.other.testnetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * 抛弃服务器
 * @author Wen, Changying
 * 2019年6月27日
 */
public class DiscardServer {

	private int port;
	public DiscardServer() {
		this(8000);
	}
	public DiscardServer(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			new DiscardServer(Integer.parseInt(args[0]));
			return;
        }
		new DiscardServer().run();
	}
	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {

						/*//out_3. 将Encoder转换后的ByteBuf写入回写数据
						ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
							@Override
						    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//						        ByteBuf encoded = ctx.alloc().buffer(4*msg.toString().length());
//						        String res = "you type: "+msg.toString()+"\r\n";
//						        encoded.writeBytes(res.getBytes());
//						        ctx.write(encoded, promise); // (1)
//						        ctx.flush();
								ByteBuf encoded11 = (ByteBuf) msg;
						        //String res = "you type: "+msg.toString()+"\r\n";
						        //encoded.writeBytes(res.getBytes());
						        ctx.write(encoded11, promise); // (1)
						        ctx.flush();
						    }
						});*/
						//out_2. 将String类型回写数据转为ByteBuf。因为客户端读取
						ch.pipeline().addLast(new StringEncoder());
						//out_1. 写入回写数据(String)
//						ch.pipeline().addLast(new ChannelOutboundHandlerAdapter() {
//							@Override
//						    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
//						        String res = "you type: "+msg.toString()+"---from server\r\n";
//						        ctx.writeAndFlush(res);
//						    }
//						});
						//ch.pipeline().addLast(new TimeServerHandler());
						ch.pipeline().addLast(new StringDecoder());//将ByteBuf类型的输入数据转为String
						ch.pipeline().addLast(new SimpleChannelInboundHandler<String>() {
							@Override
							protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
								System.out.println("\r\nget input:\r\n"+msg);
								msg+="---from server";
								//ctx.writeAndFlush("i'm inbound".getBytes());
								//ctx.writeAndFlush("\r\nyou type:i don't know\r\n");
								ctx.writeAndFlush(msg);
								//ctx.fireChannelActive();
								//Request.parseUri(msg);
								//new Response()
							}
						});
						

					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);
			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync();
			//ChannelFuture f = b.bind(InetAddress.getByName("127.0.0.1"), port).sync();
			//手动关闭channel
			f.channel().closeFuture().sync();
		}finally{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
