package cn.itcast.hiss.message.server.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.json.JSONUtil;
import cn.itcast.hiss.execption.auth.AuthSignExecption;
import cn.itcast.hiss.execption.auth.ParamExecption;
import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageAuthType;
import cn.itcast.hiss.message.server.perporties.TcpReceiverSource;
import cn.itcast.hiss.message.server.service.TcpAuthCheckService;

import java.util.Map;

/**
 * TcpAuthCheckServiceImpl
 *
 * @author: wgl
 * @describe: TCP权限校验实现类
 * @date: 2023/5/15 11:47
 **/
public class TcpAuthCheckServiceImpl implements TcpAuthCheckService {

    @Override
    public void authCheck(Message message, TcpReceiverSource receiverSource) {
        this.checkHeader(message, receiverSource);
        MessageAuth messageAuth = message.getMessageAuth();
        if (messageAuth != null) {
            this.checkSign(messageAuth, receiverSource);
            MessageAuthType authType = messageAuth.getAuthType();
            if (authType != null && authType != MessageAuthType.NONE) {

                // basic 认证
                if (messageAuth.getAuthType() == MessageAuthType.BASIC) {
                    this.checkBasic(messageAuth, receiverSource);
                }

                // bearer 认证
                if (messageAuth.getAuthType() == MessageAuthType.BEARER) {
                    this.checkBearer(messageAuth, receiverSource);
                }

            }
        } else {
            throw new ParamExecption(String.format("缺失鉴权信息"));
        }
    }

    /**
     * 检查请求中必须包含的头信息
     *
     * @param httpReceiverSource
     */
    private void checkHeader(Message message, TcpReceiverSource httpReceiverSource) {
        Map<String, String> maps = httpReceiverSource.getHeaders();
        if (maps != null) {
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String content = message.getMessageAuth().getContent();
                if (!entry.getValue().equals(JSONUtil.toBean(content, Map.class).get(entry.getKey()))) {
                    throw new AuthSignExecption(String.format("Not Found Header {}", entry.getKey()));
                }
            }
        }
    }

    /**
     * 对于消息设置签名信息
     *
     * @param httpReceiverSource
     */
    private void checkSign(MessageAuth messageAuth, TcpReceiverSource httpReceiverSource) {
        if (httpReceiverSource.isEncrypt()) {
            String temp = MD5.create().digestHex(String.format("%s%s", messageAuth.getEncryptTime(), httpReceiverSource.getAccessToken()));
            String sign = messageAuth.getEncryptContent();
            if (!temp.equals(sign)) {
                throw new AuthSignExecption(String.format("Sign:{} != Temp:{}", sign, temp));
            }
        }
    }

    /**
     * 验证Bearer
     *
     * @param messageAuth
     * @param httpReceiverSource
     */
    private void checkBearer(MessageAuth messageAuth, TcpReceiverSource httpReceiverSource) {
        Assert.notEmpty(httpReceiverSource.getBearerToken(), messageAuth.getTenant() + "租户的Bearer Token未设置");
        String basic = messageAuth.getContent();
        String temp = String.format("Bearer " + httpReceiverSource.getBearerToken());
        if (!temp.equals(basic)) {
            throw new AuthSignExecption(String.format("Bearer Authorization Fail:{} != {}", basic, temp));
        }
    }

    /**
     * 验证Basic
     *
     * @param messageAuth
     * @param httpReceiverSource
     */
    private void checkBasic(MessageAuth messageAuth, TcpReceiverSource httpReceiverSource) {
        Assert.notEmpty(httpReceiverSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户名未设置");
        Assert.notEmpty(httpReceiverSource.getBasicUsername(), messageAuth.getTenant() + "租户的Basic用户密码未设置");
        String temp = String.format("%s:%s", httpReceiverSource.getBasicUsername(), httpReceiverSource.getBasicPassword());
        temp = String.format("Basic " + Base64.encode(temp));
        String basic = messageAuth.getContent();
        if (!temp.equals(JSONUtil.toBean(messageAuth.getContent(), Map.class).get("Autorization"))) {
            throw new AuthSignExecption(String.format("Basic Authorization Fail:{} != {}", basic, temp));
        }
    }


}
