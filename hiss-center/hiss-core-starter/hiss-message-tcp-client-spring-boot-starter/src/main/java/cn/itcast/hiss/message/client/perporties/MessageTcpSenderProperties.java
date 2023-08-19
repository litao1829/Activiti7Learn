package cn.itcast.hiss.message.client.perporties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/*
 * @author miukoo
 * @description 读取配置的租户的远程调用地址
 * @date 2023/5/13 15:32
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.message.tcp.sender")
@Data
public class MessageTcpSenderProperties {

    /**
     * 获取每个租户的远程调用地址
     */
    Map<String, TcpSenderSource> sources;

}
