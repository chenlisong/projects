package com.example.demo.netty.handler;

import com.example.demo.bean.SmartProtocol;
import com.example.demo.service.CustomerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ServerLoopHandler extends ChannelInboundHandlerAdapter{

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CustomerService customerService;

    private static Logger logger = LoggerFactory.getLogger(ServerLoopHandler.class);

    /**
     * 超过5秒触发该时间
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                logger.info("lost client {} senconds", 5);
                String str = "server: heart bong bong ... ";
                SmartProtocol response = new SmartProtocol(str.getBytes().length, str.getBytes());
                ctx.writeAndFlush(response);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SmartProtocol body = (SmartProtocol) msg;
        logger.info("server: client access, address: {}, msg: {}", ctx.channel().remoteAddress(), body.toString());
        redisTemplate.opsForValue().increment("netty-clients-numbers", 1);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
