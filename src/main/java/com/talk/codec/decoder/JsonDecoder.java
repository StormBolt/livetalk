package com.talk.codec.decoder;

import com.talk.codec.Decoder;

import io.netty.buffer.ByteBuf;

/**
 * 功能描述：
 * <p/>
 * <p/>
 * Created by dujiacheng.
 * Date: 2016/1/18
 */
public class JsonDecoder  extends Decoder {

    @Override
    protected Object transformData(Object msg) throws Exception {
        if (!(msg instanceof ByteBuf)) {
            return msg;
        }
        ByteBuf buf = (ByteBuf)msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String data= new String(req,"UTF-8");

        return data;
    }

}
