package cn.itcast.hiss.message.client.perporties;

import cn.itcast.hiss.message.MessageAuthType;
import lombok.Data;

import java.util.Map;

/*
 * @author miukoo
 * @description 远程调用的http地址
 * @date 2023/5/13 15:37
 * @version 1.0
 **/
@Data
public class TcpSenderSource {

    private int connectTimeout = 5000;
    private int readTimeout = 5000;
    // 是否签名，签名算法是MD5(time+accessToken)
    private boolean encrypt;
    // 授权Token
    private String accessToken;

    // 授权类型
    private MessageAuthType authType = MessageAuthType.NONE;
    // basic 认证的用户信息
    private String basicUsername;
    private String basicPassword;

    // bearer
    private String bearerToken;

    // 请求头信息
    private Map<String,String> headers;

}
