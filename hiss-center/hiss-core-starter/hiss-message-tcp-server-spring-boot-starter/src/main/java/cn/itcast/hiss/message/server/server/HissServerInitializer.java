package cn.itcast.hiss.message.server.server;

import cn.itcast.hiss.message.server.server.handler.HissServerHeartbeatHandler;
import cn.itcast.hiss.message.server.server.handler.HissServerMessageHandler;
import cn.itcast.hiss.tcp.TcpConstants;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务端初始化
 */
public class HissServerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private HissServerMessageHandler hissServerMessageHandler;
    @Autowired
    private HissServerHeartbeatHandler hissServerHeartbeatHandler;

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
        pipeline.addLast(new IdleStateHandler(0,0,180));
        pipeline.addLast(hissServerHeartbeatHandler);
        pipeline.addLast(hissServerMessageHandler);
    }
}
