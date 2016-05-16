package com.talk.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.log4j.Logger;

import com.talk.codec.Header;
import com.talk.codec.Message;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    private static final Logger logger = Logger.getLogger(ClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
        Message message = (Message) msg;
        String data = (String)message.getData();
        System.out.println("客户端收到服务器信息: " + data);
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("连接服务器成功...");
        Header header = new Header("11111121111111111212111111111111");
        header.setCommandId(100);
        Message message = new Message(header,"hello,server");
       
        ctx.writeAndFlush(message);
    }
    
}
