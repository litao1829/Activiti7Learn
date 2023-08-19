package cn.itcast.hiss.message.server.perporties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @author miukoo
 * @description 读取tcp配置
 * @date 2023/5/13 15:32
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.message.tcp")
@Data
public class MessageTcpServerProperties {
    int port = 10000;
    // 同步读取数据的超时时间
    int readTimeout = 5000;
}
