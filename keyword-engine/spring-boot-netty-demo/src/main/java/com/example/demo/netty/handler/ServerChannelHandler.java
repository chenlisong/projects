package com.example.demo.netty.handler;

import com.example.demo.netty.smart.SmartDecoder;
import com.example.demo.netty.smart.SmartEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServerChannelHandler extends ChannelInitializer<SocketChannel> {

    @Autowired
    private ServerLoopHandler serverLoopHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 添加自定义协议的编解码工具
        ch.pipeline().addLast(new SmartEncoder());
        ch.pipeline().addLast(new SmartDecoder());
        // 处理网络IO
        ch.pipeline().addLast(serverLoopHandler);
    }
}
