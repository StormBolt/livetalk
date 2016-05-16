package com.talk.codec.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

import com.talk.codec.Encoder;

/**
 * 功能描述：
 * <p/>
 * <p/>
 * Created by dujiacheng.
 * Date: 2016/1/18
 */
public class JsonEncoder extends Encoder {

    @Override
    protected Object transformData(Object msg) throws Exception {
        if (msg instanceof String){
            String data = (String)msg;
            if(data.length() != 0) {
                Charset utf8 = Charset.forName("UTF-8");
                ByteBuf encoded = Unpooled.copiedBuffer(data, utf8);

                return encoded;
            }
        }
        return null;
    }
}
