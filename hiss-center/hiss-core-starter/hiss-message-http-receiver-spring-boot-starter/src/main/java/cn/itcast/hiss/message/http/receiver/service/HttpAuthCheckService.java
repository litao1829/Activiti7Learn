package cn.itcast.hiss.message.http.receiver.service;

import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.http.receiver.properties.HttpReceiverSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @author miukoo
 * @description 验证Http请求是否合法
 * @date 2023/5/13 16:01
 * @version 1.0
 **/
public interface HttpAuthCheckService {

    /**
     * 验证请求的合法性
     * @param request
     * @param response
     * @param message
     * @param httpSenderSource
     */
    public void authCheck(HttpServletRequest request, HttpServletResponse response, DefaultMessage message, HttpReceiverSource httpSenderSource);


}
