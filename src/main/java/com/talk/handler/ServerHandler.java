package com.talk.handler;

import java.util.concurrent.ConcurrentHashMap;

import com.talk.codec.Header;
import com.talk.codec.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	static ConcurrentHashMap<String, ChannelHandlerContext> hashMap  = new ConcurrentHashMap<String, ChannelHandlerContext>();



	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)throws Exception {
		Message message = (Message) msg;
		String content = (String)message.getData();
		System.out.println("收到客户端消息: " + content);

		hashMap.put(message.getHeader().getSessionid(),ctx);

		Header header = new Header("11112111111111111111111111111111");
		header.setCommandId(101);
		Message rep = new Message(header,"当前在线人数: " + hashMap.size());
		
		for (ChannelHandlerContext ch : hashMap.values()) {
			ch.writeAndFlush(rep);
		}
		
	}



}
