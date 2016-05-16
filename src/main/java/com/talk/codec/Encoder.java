package com.talk.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * 功能描述：
 * <p/>
 * <p/>
 * Created by dujiacheng.
 * Date: 2016/1/18
 */
public abstract class Encoder extends MessageToMessageEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext var1, Message msg, List<Object> out) throws Exception {
        Message message = (Message) msg;
        Object buffer = transformData(message.getData());
        message.setData(buffer);
        out.add(message);
    }

    /**
     * 将逻辑对象转换成二进制数据
     * @param msg
     * @throws Exception
     */
    protected abstract Object transformData(Object msg) throws Exception;

}
