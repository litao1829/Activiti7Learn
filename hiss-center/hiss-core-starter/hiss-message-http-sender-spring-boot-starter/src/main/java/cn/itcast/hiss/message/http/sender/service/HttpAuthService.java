package cn.itcast.hiss.message.http.sender.service;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.http.sender.properties.HttpSenderSource;
import org.springframework.http.HttpHeaders;

/*
 * @author miukoo
 * @description 生成HttpService
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface HttpAuthService {

    /**
     * 发送消息时的授权信息创建
     * @param headers
     * @param message
     */
    public void authCreate(HttpHeaders headers, Message message, HttpSenderSource httpSenderSource);


}
