package cn.itcast.hiss.message.http.sender.sender;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.http.sender.service.HttpSenderService;
import cn.itcast.hiss.message.sender.Sender;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * @author miukoo
 * @description Http消息发送器实现
 * @date 2023/5/13 15:34
 * @version 1.0
 **/
public class MessageHttpSender extends Sender {

    @Autowired
    HttpSenderService httpSenderService;
    @Override
    public void send(MessageContext messageContext, Message message) {
        httpSenderService.send(this,messageContext,message);
    }

    @Override
    public String typeName() {
        return "http";
    }
}
