package com.talk.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * 功能描述：
 * <p/>
 * <p/>
 * Created by dujiacheng.
 * Date: 2016/1/18
 */
public abstract class Decoder extends MessageToMessageDecoder<Message> {

        @Override
        protected void decode(ChannelHandlerContext ctx, Message msg, List<Object> out)throws Exception{
            if (!(msg instanceof Message)) {
                out.add(msg);
            }
            Message message = (Message) msg;
            Object logicObj = transformData(message.getData());
            message.setData(logicObj);
            out.add(message);
        }

        /**
         * 将二进制数据转换成逻辑对象
         *
         * @param msg
         * 					请求对象
         * @throws Exception
         */
        protected abstract Object transformData(Object msg) throws Exception;

}
