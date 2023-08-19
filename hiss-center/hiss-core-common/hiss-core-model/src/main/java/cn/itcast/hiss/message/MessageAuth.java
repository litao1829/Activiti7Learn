package cn.itcast.hiss.message;

import lombok.Data;

/**
 * 消息授权信息
 * @author miukoo
 */
@Data
public class MessageAuth {

    // 租户
    String tenant;
    // 是否签名
    boolean encrypt;
    // 签名算法生成时间
    long encryptTime = 0l;
    // 签名
    String encryptContent;
    // 授权类型
    MessageAuthType authType = MessageAuthType.NONE;
    // 授权内容
    String content;
    //当前操作用户
    CurrentUser currentUser;

}