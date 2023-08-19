package cn.itcast.hiss.message.http.receiver.service;

import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.Sender;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @author miukoo
 * @description HTTP接收器的Service
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface HttpReceiverService {

    public MessageContext receiver(DefaultMessage message,
                         HttpServletRequest request, HttpServletResponse response);

}
