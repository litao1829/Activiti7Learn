package cn.itcast.hiss.message.sender;

import cn.itcast.hiss.message.MessageCallback;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.Message;

/**
 * 消息发送器: 暴露给外部模块调用，进行消息的发送
 * @author miukoo
 */
public interface MessageSenderExecuter {

    /**
     * 发送消息
     * @param message
     * @return
     */
    public MessageContext sendMessage(Message message);

    /**
     * 异步发送
     * @param message
     * @param messageCallback
     */
    public void sendMessage(Message message, MessageCallback messageCallback);

}
