package cn.itcast.hiss.message;

/*
 * @author miukoo
 * @description 消息的发送和成功之后，回调业务的类
 * @date 2023/5/12 21:39
 * @version 1.0
 **/
public interface MessageCallback {
    void callback(MessageContext messageContext,Message message);

}
