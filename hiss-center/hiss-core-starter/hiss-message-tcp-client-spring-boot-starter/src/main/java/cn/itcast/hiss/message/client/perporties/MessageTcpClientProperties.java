package cn.itcast.hiss.message.client.perporties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @author miukoo
 * @description 读取tcp配置
 * @date 2023/5/13 15:32
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.message.tcp")
@Data
public class MessageTcpClientProperties {
    // 主机地址
    String host = "localhost:10003";
    // 链接超时时间
    int connectTimeout = 30000;
    // 同步读取数据的超时时间
    int readTimeout = 5000;
    // 最大的重连次数
    int maxConnectCount = 3;
    // 重试时间间隔
    int retryInterval = 5;
    // 重连时间间隔
    int reconnectionInterval = 60;
}
