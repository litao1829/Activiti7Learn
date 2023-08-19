package cn.itcast.hiss.message.http.sender.service;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.Sender;

/*
 * @author miukoo
 * @description HTTP发送器的Service
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface HttpSenderService {

    public void send(Sender sender, MessageContext messageContext, Message message);

}
