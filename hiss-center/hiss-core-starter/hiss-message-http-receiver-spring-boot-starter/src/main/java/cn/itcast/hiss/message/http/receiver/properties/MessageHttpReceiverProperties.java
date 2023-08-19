package cn.itcast.hiss.message.http.receiver.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/*
 * @author miukoo
 * @description 读取配置的租户的远程调用地址
 * @date 2023/5/13 15:32
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.message.http.receiver")
@Data
public class MessageHttpReceiverProperties {

    /**
     * 获取每个租户的远程调用地址
     */
    Map<String, HttpReceiverSource> sources;

}
