package com.example.demo.netty.handler;

import com.example.demo.bean.SmartProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private static Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    private int loopTimes = 1;

    private int loopTimeMax = 3;

//    private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Heartbeat",
//            CharsetUtil.UTF_8));

    private static final String HEARTBEAT_SEQUENCE = "Heartbeat";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client: access server: " + new Date());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("client: close: " + new Date());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("client: happen idle event: " + new Date());
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                if(loopTimeMax > loopTimes++) {
                    SmartProtocol smartProtocol = new SmartProtocol(HEARTBEAT_SEQUENCE.getBytes().length, HEARTBEAT_SEQUENCE.getBytes());
                    ctx.channel().writeAndFlush(smartProtocol);
                }
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SmartProtocol body = (SmartProtocol) msg;
        logger.info("client: access message: {} time is {} ", body.toString(), new Date());
        if (new String(body.getContent()).equals("Heartbeat")) {
            ctx.write("client: has read message from server");
            ctx.flush();
        }
        ReferenceCountUtil.release(msg);
    }
}
