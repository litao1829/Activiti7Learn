package cn.itcast.hiss.message.http.receiver.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.itcast.hiss.execption.auth.AuthSignExecption;
import cn.itcast.hiss.execption.auth.ParamExecption;
import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageAuthType;
import cn.itcast.hiss.message.http.receiver.properties.HttpReceiverSource;
import cn.itcast.hiss.message.http.receiver.service.HttpAuthCheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * @author miukoo
 * @description 进行权限统一拦截和校验
 * @date 2023/5/13 16:11
 * @version 1.0
 **/
@Slf4j
public class HttpAuthCheckServiceImpl implements HttpAuthCheckService {

    @Override
    public void authCheck(HttpServletRequest request, HttpServletResponse response, DefaultMessage message, HttpReceiverSource httpReceiverSource) {
        // 排除不进行鉴权校验的实现
        if(httpReceiverSource.getExcludeAuthIds()!=null){
            if(httpReceiverSource.getExcludeAuthIds().contains(message.getId())){
                return;
            }
        }
        this.checkHeader(request,httpReceiverSource);
        MessageAuth messageAuth = message.getMessageAuth();
        if(messageAuth!=null){
            this.checkSign(messageAuth,request,httpReceiverSource);
            MessageAuthType authType = messageAuth.getAuthType();
            if(authType!=null&&authType!=MessageAuthType.NONE){

                // basic 认证
                if(messageAuth.getAuthType() == MessageAuthType.BASIC){
                    this.checkBasic(messageAuth,request,httpReceiverSource);
                }

                // bearer 认证
                if(messageAuth.getAuthType() == MessageAuthType.BEARER){
                    this.checkBearer(messageAuth,request,httpReceiverSource);
                }

                // hutool jwt 认证
                if(messageAuth.getAuthType() == MessageAuthType.HUTOOL_JWT){
                    this.checkHutoolJwt(messageAuth,request,httpReceiverSource);
                }

                // hs256 jwt 认证
                if(messageAuth.getAuthType() == MessageAuthType.JWT_HS256){
                    this.checkJwtHs256(messageAuth,request,httpReceiverSource);
                }

            }
        }else{
            throw new ParamExecption(String.format("缺失鉴权信息"));
        }
    }

    /**
     *
     * @param messageAuth
     * @param request
     * @param httpReceiverSource
     */
    private void checkJwtHs256(MessageAuth messageAuth, HttpServletRequest request, HttpReceiverSource httpReceiverSource) {
        Assert.notEmpty(httpReceiverSource.getTokenKey(),messageAuth.getTenant()+"租户的TokenKey未设置");
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            token = request.getParameter("token");
        }
        if(StrUtil.isEmpty(token)){
            token = request.getHeader("Authorization");
        }
        Assert.notEmpty(token,messageAuth.getTenant()+"租户的请求未设置token");
        if(httpReceiverSource.isEncrypt()){
            boolean verify = JWTUtil.verify(token, JWTSignerUtil.hs256(httpReceiverSource.getTokenKey().getBytes()));
            if(!verify){
                throw new AuthSignExecption("无效Token");
            }
        }
    }

    /**
     *
     * @param messageAuth
     * @param request
     * @param httpReceiverSource
     */
    private void checkHutoolJwt(MessageAuth messageAuth, HttpServletRequest request, HttpReceiverSource httpReceiverSource) {
        Assert.notEmpty(httpReceiverSource.getTokenKey(),messageAuth.getTenant()+"租户的TokenKey未设置");
        String token = request.getHeader("token");
        if(StrUtil.isEmpty(token)){
            token = request.getParameter("token");
        }
        if(StrUtil.isEmpty(token)){
            token = request.getHeader("Authorization");
        }
        Assert.notEmpty(token,messageAuth.getTenant()+"租户的请求未设置token");
        if(httpReceiverSource.isEncrypt()){
            boolean verify = JWTUtil.verify(token, httpReceiverSource.getTokenKey().getBytes());
            if(!verify){
                throw new AuthSignExecption("无效Token");
            }
        }
    }

    /**
     * 检查请求中必须包含的头信息
     * @param httpReceiverSource
     */
    private void checkHeader(HttpServletRequest request, HttpReceiverSource httpReceiverSource) {
        Map<String, String> maps = httpReceiverSource.getHeaders();
        if(maps!=null){
            for (Map.Entry<String, String> entry : maps.entrySet()) {
                String header = request.getHeader(entry.getKey());
                if(!entry.getValue().equals(header)){
                    log.error("Not Found Header {}",entry.getKey());
                    throw new AuthSignExecption("Header 认证失败");
                }
            }
        }
    }

    /**
     * 对于消息设置签名信息
     * @param httpReceiverSource
     */
    private void checkSign(MessageAuth messageAuth,HttpServletRequest request, HttpReceiverSource httpReceiverSource) {
        if(httpReceiverSource.isEncrypt()){
            String temp = MD5.create().digestHex(String.format("%s%s",messageAuth.getEncryptTime(),httpReceiverSource.getAccessToken()));
            String sign = messageAuth.getEncryptContent();
            if(StrUtil.isEmpty(sign)){
                sign = request.getHeader("hiss_sign");
            }
            if(!temp.equals(sign)){
                log.error("签名错误：Sign:%s != Temp:%s",sign,temp);
                throw new AuthSignExecption("签名错误");
            }
        }
    }

    /**
     * 验证Bearer
     * @param messageAuth
     * @param request
     * @param httpReceiverSource
     */
    private void checkBearer(MessageAuth messageAuth ,HttpServletRequest request, HttpReceiverSource httpReceiverSource){
        Assert.notEmpty(httpReceiverSource.getBearerToken(),messageAuth.getTenant()+"租户的Bearer Token未设置");
        String basic = request.getHeader(HttpHeaders.AUTHORIZATION);
        String temp = String.format("Bearer "+httpReceiverSource.getBearerToken());
        if(!temp.equals(basic)){
            log.error("Bearer Authorization Fail:{} != {}",basic,temp);
            throw new AuthSignExecption("Bearer 认证失败");
        }
    }

    /**
     * 验证Basic
     * @param messageAuth
     * @param request
     * @param httpReceiverSource
     */
    private void checkBasic(MessageAuth messageAuth ,HttpServletRequest request, HttpReceiverSource httpReceiverSource){
        Assert.notEmpty(httpReceiverSource.getBasicUsername(),messageAuth.getTenant()+"租户的Basic用户名未设置");
        Assert.notEmpty(httpReceiverSource.getBasicUsername(),messageAuth.getTenant()+"租户的Basic用户密码未设置");
        String temp = String.format("%s:%s",httpReceiverSource.getBasicUsername(),httpReceiverSource.getBasicPassword());
        temp = String.format("Basic "+Base64.encode(temp));
        String basic = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(!temp.equals(basic)){
            log.error("Basic Authorization Fail:{} != {}",basic,temp);
            throw new AuthSignExecption("Basic 认证失败");
        }
    }



}
