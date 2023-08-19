package cn.itcast.hiss.message.client.service;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.Sender;
import cn.itcast.hiss.tcp.TcpMessage;

/**
 * TcpSenderService
 * @author: wgl
 * @describe: tcp协议消息发送器
 * @date: 2023/5/15 15:45
 **/
public interface TcpSenderService {

    public void send(Sender sender, MessageContext messageContext, TcpMessage message);

}
