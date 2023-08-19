package cn.itcast.hiss.message.server.service;

import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.server.perporties.TcpReceiverSource;

/*
 * @author miukoo
 * @description 验证Http请求是否合法
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface TcpAuthCheckService {

    /**
     * 验证请求的合法性
     *
     * @param message
     * @param tcpReceiverSource
     */
    public void authCheck(Message message, TcpReceiverSource tcpReceiverSource);


}
