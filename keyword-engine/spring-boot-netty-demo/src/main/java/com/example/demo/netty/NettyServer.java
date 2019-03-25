package com.example.demo.netty;

import com.example.demo.netty.handler.ServerChannelHandler;
import com.example.demo.netty.handler.ServerLoopHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 1. 实现自定义通信协议，自实现拆包，粘包 fix
 * 2. 心跳检测客户端动态 fix
 * 3. 定时push数据 fix
 * 4. 可操作mysql，redis fix
 */
@Component
public class NettyServer {

    private static int port = 8888;

    @Autowired
    private ServerChannelHandler serverChannelHandler;

    private static Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @PostConstruct
    public void startServer() {
        Executors.newSingleThreadExecutor().execute(() -> {
            EventLoopGroup bossGroup = new NioEventLoopGroup(1);
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap sbs = new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.INFO)).localAddress(new InetSocketAddress(port))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
//                                ch.pipeline().addLast("decoder", new StringDecoder());
//                                ch.pipeline().addLast("encoder", new StringEncoder());
                                ch.pipeline().addLast(serverChannelHandler);
                            };

                        }).option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);
                // 绑定端口，开始接收进来的连接
                ChannelFuture future = sbs.bind(port).sync();

                logger.info("server: netty server start at port: {}", port);
                future.channel().closeFuture().sync();
            } catch (Exception e) {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });
    }

}
