package cn.itcast.hiss.message.client.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageAuthType;
import cn.itcast.hiss.message.client.perporties.TcpSenderSource;
import cn.itcast.hiss.message.client.service.TcpAuthService;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/13 16:11
 * @version 1.0
 **/
public class TcpAuthServiceImpl implements TcpAuthService {

    @Override
    public void authCreate(Message message, TcpSenderSource tcpSenderSource) {
        Map<String, Object> tcpAuth = new HashMap<>();
        // 设置请求固定头信息
        MessageAuth messageAuth = message.getMessageAuth();
        Map<String, String> maps = tcpSenderSource.getHeaders();
        if (maps != null) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                tcpAuth.put(entry.getKey(), entry.getValue());
            }
        }
        // 设置basic或者bearer
        if (messageAuth != null) {
            MessageAuthType authType = messageAuth.getAuthType();
            if (authType != null && authType != MessageAuthType.NONE) {

                // basic 认证
                if (messageAuth.getAuthType() == MessageAuthType.BASIC) {
                    this.createBasic(messageAuth, tcpAuth, tcpSenderSource);
                }

                // bearer 认证
                if (messageAuth.getAuthType() == MessageAuthType.BEARER) {
                    this.createBearer(messageAuth, tcpAuth, tcpSenderSource);
                }
            }
        }
        // 设置消息的签名信息
        this.sign(messageAuth, tcpAuth, message, tcpSenderSource);
        message.getMessageAuth().setContent(JSON.toJSONString(tcpAuth));
    }

    /**
     * 对于消息设置签名信息
     *
     * @param tcpMap
     * @param message
     * @param tcpSenderSource
     */
    private void sign(MessageAuth messageAuth, Map tcpMap, Message message, TcpSenderSource tcpSenderSource) {
        if (tcpSenderSource.isEncrypt()) {
            messageAuth.setEncryptTime(System.currentTimeMillis());
            String temp = String.format("%s%s", messageAuth.getEncryptTime(), tcpSenderSource.getAccessToken());
            messageAuth.setEncryptContent(MD5.create().digestHex(temp));
            messageAuth.setEncrypt(true);
            tcpMap.put("hiss_sign", messageAuth.getEncryptContent());
        }
    }

    /**
     * 生成basic头信息
     *
     * @param messageAuth
     * @param tcpMap
     * @param tcpSenderSource
     */
    private void createBearer(MessageAuth messageAuth, Map tcpMap, TcpSenderSource tcpSenderSource) {
        Assert.notEmpty(tcpSenderSource.getBearerToken(), messageAuth.getTenant() + "租户的Bearer Token未设置");
        tcpMap.put("Authorization", "Bearer " + tcpSenderSource.getBearerToken());
    }

    /**
     * 生成basic头信息
     *
     * @param messageAuth
     * @param tcpAuth
     * @param tcpSenderSource
     */
    private void createBasic(MessageAuth messageAuth, Map tcpAuth, TcpSenderSource tcpSenderSource) {
        Assert.notEmpty(tcpSenderSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户名未设置");
        Assert.notEmpty(tcpSenderSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户密码未设置");
        String temp = String.format("%s:%s", tcpSenderSource.getBasicUsername(), tcpSenderSource.getBasicPassword());
        tcpAuth.put("Autorization", "Basic " + Base64.encode(temp));
    }
}