package com.talk.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 对包的头文件进行编码
 * @author zhaohui
 *
 */
public class HeaderEncoder extends MessageToByteEncoder {


	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, Object msg, ByteBuf byteBuf) throws Exception {
		if (!(msg instanceof Message)) {
			return;
		}
		Message message = (Message) msg;
		ByteBuf buffer = (ByteBuf) message.getData();
		Header header = message.getHeader();

		byteBuf.writeByte(HeaderDecoder.PACKAGE_TAG);
		byteBuf.writeByte(header.getEncode());
		byteBuf.writeByte(header.getEncrypt());
		byteBuf.writeByte(header.getExtend1());
		byteBuf.writeByte(header.getExtend2());
		byteBuf.writeBytes(header.getSessionid().getBytes());
		byteBuf.writeInt(buffer.readableBytes());
		byteBuf.writeInt(header.getCommandId());
		byteBuf.writeBytes(buffer);
	}
}
