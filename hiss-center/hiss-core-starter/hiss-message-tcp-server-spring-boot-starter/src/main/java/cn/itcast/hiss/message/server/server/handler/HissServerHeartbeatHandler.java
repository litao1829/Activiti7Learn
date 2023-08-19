package cn.itcast.hiss.message.server.server.handler;

import cn.itcast.hiss.message.server.message.MessageSessionFactory;
import cn.itcast.hiss.tcp.TcpConstants;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/*
 * @author miukoo
 * @description 处理心跳信息
 * @date 2023/5/27 9:06
 * @version 1.0
 **/
@ChannelHandler.Sharable
@Slf4j
public class HissServerHeartbeatHandler extends SimpleChannelInboundHandler<String> {

    ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        channels.add(ctx.channel());
        log.info("新客户端[ip:{}]连接，当前共用{}客户端",clientIp,channels.size());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.trace("server channelRead: {}", msg);
        // 注册的ping需要带上租户信息，多个租户已逗号分隔
        if(msg.startsWith("ping:")){
            // 注册客户端多租户的信息
            MessageSessionFactory.registerByPing(msg,ctx);
            ctx.channel().writeAndFlush("pong"+ TcpConstants.MESSAGE_DELIMITER);
        }else{
            ctx.fireChannelRead(msg);
        }
    }

    /**
     * 主动断开的则清理
     * @param ctx
     */
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        log.info("客户端主动断开{}", channel.remoteAddress());
        MessageSessionFactory.clearChannel(channel);
        ctx.flush();
    }



    /**
     * 3分钟没有收到读写消息，则关闭通道
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.ALL_IDLE) {
                Channel channel = ctx.channel();
                log.info("关闭超时3分钟未通信的通道{}", channel.remoteAddress());
                MessageSessionFactory.clearChannel(channel);
                channel.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

}
