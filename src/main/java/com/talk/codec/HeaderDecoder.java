package com.talk.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 功能描述：
 * <p/>
 * <p/>
 * Created by dujiacheng.
 * Date: 2016/1/18
 */
public class HeaderDecoder extends ByteToMessageDecoder {

    /**头文件长度**/
    public static final int HEAD_LENGHT = 45;
    /** 包头标志 **/
    public static final byte PACKAGE_TAG = 0x01;

    protected void decode(ChannelHandlerContext var1, ByteBuf buffer, List<Object> out) throws Exception{
        if (buffer.readableBytes() < HEAD_LENGHT) {
            return ;
        }
        buffer.markReaderIndex();
        byte tag = buffer.readByte();
        if (tag != PACKAGE_TAG) {
            throw new Exception("非法协议包");
        }
        byte encode = buffer.readByte();
        byte encrypt = buffer.readByte();
        byte extend1 = buffer.readByte();
        byte extend2 = buffer.readByte();
        byte sessionByte[] = new byte[32];
        buffer.readBytes(sessionByte);
        String sessionid = new String(sessionByte);
        int length = buffer.readInt();
        int commandId = buffer.readInt();

        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return;
        }

        Header header = new Header(encode, encrypt, extend1, extend2,
                sessionid, length, commandId);
        Message message = new Message(header, buffer.readBytes(length));

        out.add(message);
    }

}

