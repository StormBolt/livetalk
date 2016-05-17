package com;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.talk.codec.HeaderDecoder;
import com.talk.codec.HeaderEncoder;
import com.talk.codec.decoder.JsonDecoder;
import com.talk.codec.encoder.JsonEncoder;
import com.talk.handler.ClientHandler;
import com.talk.mode.Request;
import org.apache.log4j.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class);
    public static String host = "127.0.0.1";
    public static int port = 8080;

    /**
     * @param args
     * @throws InterruptedException 
     * @throws IOException 
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
            .channel(NioSocketChannel.class)
            .remoteAddress(new InetSocketAddress(host, port))
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast("decoder", new HeaderDecoder());
                    pipeline.addLast("pDecoder", new JsonDecoder());

                    pipeline.addLast("hEncoder", new HeaderEncoder());
                    pipeline.addLast("pEncoder", new JsonEncoder());

                    // 客户端的逻辑
                    pipeline.addLast("handler", new ClientHandler());
                }
            });

            ChannelFuture f = b.connect().sync();        //6

            f.channel().closeFuture().sync();            //
            logger.info("netty client start, listening on port 8080 ...");
            
        } finally {
            // The connection is closed automatically on shutdown.
            group.shutdownGracefully();
        }
    }
}
