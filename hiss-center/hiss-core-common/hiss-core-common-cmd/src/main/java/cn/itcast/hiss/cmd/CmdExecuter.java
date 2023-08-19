package cn.itcast.hiss.cmd;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageCallback;
import cn.itcast.hiss.message.MessageContext;

/**
 * CmdExecuter
 * @author: wgl
 * @describe: 流程拦截器统一入口
 * @date: 2023/5/13 23:13
 **/
public interface CmdExecuter {

    /**
     * 同步开始处理拦截器
     * @param message
     * @return
     */
    public MessageContext executer(Message message);

    /**
     * 异步开始处理拦截器
     * @param message
     * @param messageCallback
     */
    public void executer(Message message, MessageCallback messageCallback);
}