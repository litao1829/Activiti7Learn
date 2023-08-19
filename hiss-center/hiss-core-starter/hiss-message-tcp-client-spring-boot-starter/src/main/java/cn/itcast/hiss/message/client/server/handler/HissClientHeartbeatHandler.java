package cn.itcast.hiss.message.client.server.handler;

import cn.itcast.hiss.message.client.client.HissNettyClient;
import cn.itcast.hiss.message.client.perporties.MessageTcpClientProperties;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/27 10:07
 * @version 1.0
 **/
@ChannelHandler.Sharable
@Slf4j
public class HissClientHeartbeatHandler extends SimpleChannelInboundHandler<String> {
    @Autowired
    MessageTcpClientProperties messageTcpClientProperties;
    @Autowired
    HissNettyClient hissNettyClient;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if(msg.equals("pong")){
            log.trace("收到心跳回复");
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                hissNettyClient.writePing(ctx.channel());
                log.trace("发送心跳数据");
            } else if (idleStateEvent.state() == IdleState.READER_IDLE) {
                log.trace("关闭无用的通道:{}", ctx.channel().remoteAddress());
                ctx.channel().close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("channelInactive:{}", ctx.channel().localAddress());
        ctx.pipeline().remove(this);
        ctx.channel().close();
        reconnection(ctx);
    }

    /** 入栈发生异常时执行exceptionCaught */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            log.warn("客户端[{}]和远程断开连接", ctx.channel().localAddress());
        } else {
            log.error(cause.getMessage());
        }
        ctx.pipeline().remove(this);
        ctx.channel().close();
        reconnection(ctx);
    }

    private void reconnection(ChannelHandlerContext ctx) {
        log.warn("{}s之后重新建立连接",messageTcpClientProperties.getRetryInterval());
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                hissNettyClient.startSignelConnect(ctx.channel());
            }
        }, messageTcpClientProperties.getRetryInterval(), TimeUnit.SECONDS);
    }

}
