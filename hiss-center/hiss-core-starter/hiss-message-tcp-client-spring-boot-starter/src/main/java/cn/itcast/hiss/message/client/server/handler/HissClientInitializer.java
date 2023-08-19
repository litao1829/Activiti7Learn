package cn.itcast.hiss.message.client.server.handler;

import cn.itcast.hiss.tcp.TcpConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务端初始化
 */
@ChannelHandler.Sharable
public class HissClientInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private HissClientMessageHandler hissClientMessageHandler;
    @Autowired
    private HissClientHeartbeatHandler hissClientHeartbeatHandler;

    /**
     * 初始化channel
     */
    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        ByteBuf delimiter = Unpooled.copiedBuffer(TcpConstants.MESSAGE_DELIMITER.getBytes());
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024*1024*10, delimiter));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        //180s没有接收到任何的消息，表示通道已经无用了，直接关闭
        //30s内既没有read又没有write操作则触发ALL_IDLE事件,发送ping/pong心跳续约包
        pipeline.addLast(new IdleStateHandler(180,0,30));
        pipeline.addLast(hissClientHeartbeatHandler);
        pipeline.addLast(hissClientMessageHandler); // 添加客户端处理器
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }

}
