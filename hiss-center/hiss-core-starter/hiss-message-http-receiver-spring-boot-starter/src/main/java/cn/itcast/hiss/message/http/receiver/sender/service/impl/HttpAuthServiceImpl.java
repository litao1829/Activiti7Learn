package cn.itcast.hiss.message.http.receiver.sender.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageAuthType;
import cn.itcast.hiss.message.http.receiver.properties.HttpSenderSource;
import cn.itcast.hiss.message.http.receiver.sender.service.HttpAuthService;
import org.springframework.http.HttpHeaders;

import java.util.Map;

/**
 * HttpAuthServiceImpl
 * @author: wgl
 * @describe: 服务端-》客户端消息权限认证
 * @date: 2023/5/26 17:27
 **/
public class HttpAuthServiceImpl implements HttpAuthService {

    @Override
    public void authCreate(HttpHeaders headers, Message message, HttpSenderSource httpSenderSource) {
        // 设置请求固定头信息
        MessageAuth messageAuth = message.getMessageAuth();
        Map<String, String> maps = httpSenderSource.getHeaders();
        if (maps != null) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                headers.add(entry.getKey(), entry.getValue());
            }
        }
        // 设置basic或者bearer
        if (messageAuth != null) {
            MessageAuthType authType = messageAuth.getAuthType();
            if (authType != null && authType != MessageAuthType.NONE) {

                // basic 认证
                if (messageAuth.getAuthType() == MessageAuthType.BASIC) {
                    this.createBasic(messageAuth, headers, httpSenderSource);
                }

                // bearer 认证
                if (messageAuth.getAuthType() == MessageAuthType.BEARER) {
                    this.createBearer(messageAuth, headers, httpSenderSource);
                }

            }
        }
        // 设置消息的签名信息
        this.sign(messageAuth, headers, message, httpSenderSource);
    }

    /**
     * 对于消息设置签名信息
     *
     * @param headers
     * @param message
     * @param httpSenderSource
     */
    private void sign(MessageAuth messageAuth, HttpHeaders headers, Message message, HttpSenderSource httpSenderSource) {
        if (httpSenderSource.isEncrypt()) {
            messageAuth.setEncryptTime(System.currentTimeMillis());
            String temp = String.format("%s%s", messageAuth.getEncryptTime(), httpSenderSource.getAccessToken());
            messageAuth.setEncryptContent(MD5.create().digestHex(temp));
            messageAuth.setEncrypt(true);
            headers.add("hiss_sign", messageAuth.getEncryptContent());
        }
    }

    /**
     * 生成basic头信息
     *
     * @param messageAuth
     * @param headers
     * @param httpSenderSource
     */
    private void createBearer(MessageAuth messageAuth, HttpHeaders headers, HttpSenderSource httpSenderSource) {
        Assert.notEmpty(httpSenderSource.getBearerToken(), messageAuth.getTenant() + "租户的Bearer Token未设置");
        headers.setBearerAuth(httpSenderSource.getBearerToken());
    }

    /**
     * 生成basic头信息
     *
     * @param messageAuth
     * @param headers
     * @param httpSenderSource
     */
    private void createBasic(MessageAuth messageAuth, HttpHeaders headers, HttpSenderSource httpSenderSource) {
        Assert.notEmpty(httpSenderSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户名未设置");
        Assert.notEmpty(httpSenderSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户密码未设置");
        String temp = String.format("%s:%s", httpSenderSource.getBasicUsername(), httpSenderSource.getBasicPassword());
        headers.setBasicAuth(Base64.encode(temp));
    }


}
