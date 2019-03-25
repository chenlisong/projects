package com.example.demo.netty.smart;

import com.example.demo.bean.SmartProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SmartEncoder extends MessageToByteEncoder<SmartProtocol> {

    @Override
    protected void encode(ChannelHandlerContext tcx, SmartProtocol msg, ByteBuf out) throws Exception {
        // 写入消息的具体内容
        // 1.写入消息的开头的信息标志(int类型)
        out.writeInt(msg.getHead_data());
        // 2.写入消息的长度(int 类型)
        out.writeInt(msg.getContentLength());
        // 3.写入消息的内容(byte[]类型)
        out.writeBytes(msg.getContent());
    }
}