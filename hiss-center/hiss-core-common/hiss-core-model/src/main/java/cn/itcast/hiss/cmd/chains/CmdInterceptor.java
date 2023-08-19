package cn.itcast.hiss.cmd.chains;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;

/**
 * FlowReceiverChains
 *
 * @author: wgl
 * @describe: 流程处理器链路
 * @date: 2022/12/28 10:10
 */
public abstract class CmdInterceptor {

    private boolean async;

    /**
     * 设置下一个流程过滤器器
     *
     * @param next
     */
    private CmdInterceptor next;

    /**
     * 默认排序为0
     * @return
     */
    public int order() {
        return 0;
    }

    public CmdInterceptor getNext() {
        return next;
    }

    public void setNext(CmdInterceptor next) {
        this.next = next;
    }

    public void invokeMethod(MessageContext messageContext, Message message) {
        //增加节点计数器
        messageContext.addCount();
        this.invoke(messageContext, message);
    }

    /**
     * 执行拦截的逻辑方法
     * @param messageContext
     */
    public abstract void invoke(MessageContext messageContext, Message message);

    /**
     * 设置同步异步标志
     * @param flag
     */
    public void setAsync(boolean flag){
        this.async=flag;
    }

    public boolean isAsync(){
        return async;
    }
}
