package cn.itcast.hiss.message.sender.properties;

import cn.itcast.hiss.message.MessageAuthType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/12 23:55
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.message.sender")
@Data
public class MessageSenderProperties {

    /**
     * 租户id
     */
    private String tenant;

    /**
     * 是否签名
     */
    private boolean encrypt;

    /**
     * 签名
     */
    private String encryptContent;

    /**
     * 授权类型
     */
    private MessageAuthType authType = MessageAuthType.NONE;
}
