package cn.itcast.hiss.message.server.sender;

import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.Sender;
import cn.itcast.hiss.message.server.message.MessageSessionFactory;
import cn.itcast.hiss.tcp.SyncTcpMessageLock;
import cn.itcast.hiss.tcp.TcpConstants;
import cn.itcast.hiss.tcp.TcpMessage;
import cn.itcast.hiss.message.server.perporties.MessageTcpServerProperties;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * ServerTcpSender
 *
 * @author: wgl
 * @describe: 服务端Tcp消息发送器
 * @date: 2022/12/28 10:10
 */
@Slf4j
public class ServerTcpSender extends Sender {

    @Autowired
    MessageTcpServerProperties messageTcpServerProperties;

    @Override
    public void send(MessageContext messageContext, Message message) {
        String tenant = message.getMessageAuth().getTenant();
        TcpMessage tcpMessage = new TcpMessage();
        tcpMessage.setMessage(message);
        tcpMessage.setMessageId(UUID.randomUUID().toString());

        //根据租户获取对应的tcp连接
        List<Channel> channels = MessageSessionFactory.getChannels(tenant);
        if(channels!=null&&channels.isEmpty()){
            return;
        }
        Channel channel = MessageSessionFactory.getChannels(tenant).get(0);
        ChannelFuture channelFuture = channel.writeAndFlush(JSON.toJSONString(tcpMessage) + SystemConstant.NETTY_MESSAGE_SUFFIX + TcpConstants.MESSAGE_DELIMITER);
        channelFuture.addListener((ChannelFutureListener) f->{
            Throwable cause = f.cause();
            if(cause!=null){
                cause.printStackTrace();
            }
        });
        // 阻塞等待结果
        SyncTcpMessageLock syncTcpMessageLock = new SyncTcpMessageLock(messageContext,tcpMessage);
        syncTcpMessageLock.lock(messageTcpServerProperties.getReadTimeout());
    }

    @Override
    public String typeName() {
        return "tcp";
    }
}
