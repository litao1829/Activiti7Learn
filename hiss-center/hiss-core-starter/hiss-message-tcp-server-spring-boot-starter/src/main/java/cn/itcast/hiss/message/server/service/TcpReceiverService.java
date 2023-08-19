package cn.itcast.hiss.message.server.service;

import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;

/*
 * @author miukoo
 * @description HTTP接收器的Service
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface TcpReceiverService {

    public MessageContext receiver(Message message);
}
