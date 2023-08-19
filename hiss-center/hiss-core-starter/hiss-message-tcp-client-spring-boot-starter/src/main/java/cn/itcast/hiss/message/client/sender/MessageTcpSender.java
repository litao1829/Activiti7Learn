package cn.itcast.hiss.message.client.sender;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.client.perporties.MessageTcpClientProperties;
import cn.itcast.hiss.message.client.service.TcpSenderService;
import cn.itcast.hiss.message.sender.Sender;
import cn.itcast.hiss.tcp.SyncTcpMessageLock;
import cn.itcast.hiss.tcp.TcpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import java.util.UUID;

/**
 * MessageTcpSender
 *
 * @author: wgl
 * @describe: tcp流程中心消息发送器
 * @date: 2023/5/15 15:44
 **/
@Order(-1)
@Slf4j
public class MessageTcpSender extends Sender {

    @Autowired
    TcpSenderService tcpSenderService;
    @Autowired
    MessageTcpClientProperties messageTcpClientProperties;

    @Override
    public void send(MessageContext messageContext, Message message) {
        TcpMessage tcpMessage = new TcpMessage();
        tcpMessage.setMessage(message);
        tcpMessage.setMessageId(UUID.randomUUID().toString());
        tcpSenderService.send(this, messageContext, tcpMessage);
        // 阻塞等待结果
        SyncTcpMessageLock syncTcpMessageLock = new SyncTcpMessageLock(messageContext,tcpMessage);
        syncTcpMessageLock.lock(messageTcpClientProperties.getReadTimeout());
    }

    @Override
    public String typeName() {
        return "tcp";
    }
}
