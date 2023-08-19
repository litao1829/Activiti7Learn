//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package cn.itcast.hiss.message.sender.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/*
 * @author miukoo
 * @description 异步发送消息线程池
 * @date 2023/5/12 23:55
 * @version 1.0
 **/
@ConfigurationProperties(
    prefix = "hiss.message.sender.pool"
)
@Data
public class MessageSenderAsyncExecutorProperties {
    private int corePoolSize = 2;
    private int maxPoolSize = 10;
    private long keepAliveTime = 5000L;
    private int queueSize = 100;
    private String threadNamePrefix = "hiss-msa-";
    // 每次发送消息时的每个任务执行等待时长
    private int executorTimeout = 5000;
    // 优雅关闭任务，等待线程池任务执行的最大超时时间
    private int closeAwaitTime = 5000;
}
