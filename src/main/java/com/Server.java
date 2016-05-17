package com;

import com.talk.codec.HeaderDecoder;
import com.talk.codec.HeaderEncoder;
import com.talk.codec.decoder.JsonDecoder;
import com.talk.codec.encoder.JsonEncoder;
import com.talk.handler.ServerHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.log4j.Logger;

public class Server {
    private static final Logger logger = Logger.getLogger(Server.class);

	public static void main(String[] args) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                	ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("decoder", new HeaderDecoder());
                    pipeline.addLast("pDecoder", new JsonDecoder());

                    pipeline.addLast("encoder", new HeaderEncoder());
                    pipeline.addLast("pEncoder", new JsonEncoder());
                	// 自己的逻辑Handler
                	pipeline.addLast("handler", new ServerHandler());
                }
            });
            // 服务器绑定端口监听
            ChannelFuture f;
			f = b.bind(8080).sync();
            logger.info("netty service start, listening on port 8080 ...");
            // 监听服务器关闭监听
			f.channel().closeFuture().sync();
            // 可以简写为
            /* b.bind(portNumber).sync().channel().closeFuture().sync(); */
        }catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}
}
