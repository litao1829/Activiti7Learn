package cn.itcast.hiss.message.client.service;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.client.perporties.TcpSenderSource;
import org.springframework.http.HttpHeaders;

/*
 * @author miukoo
 * @description 生成HttpService
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface TcpAuthService {

    /**
     * 发送消息时的授权信息创建
     * @param message
     */
    public void authCreate(Message message, TcpSenderSource httpSenderSource);


}
