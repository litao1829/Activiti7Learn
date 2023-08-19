package cn.itcast.hiss.message.sender;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;

/*
 * @author miukoo
 * @description 消息发送器发送对象
 * @date 2023/5/12 22:00
 * @version 1.0
 **/
public abstract class Sender {
    private Sender next;

    /**
     * 设置下一个发送器
     * @param next
     */
    public void setNext(Sender next){
        this.next = next;
    }

    /**
     * 发送消息
     * @param messageContext
     */
    public abstract void send(MessageContext messageContext, Message message);

    /**
     * 发送器类型名称
     * @return
     */
    public abstract String typeName();

    /**
     * 默认排序值
     * @return
     */
    public int order(){
        return 0;
    }

    public Sender getNext() {
        return next;
    }
}
