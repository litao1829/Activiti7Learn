package cn.itcast.hiss.message.sender.config;

import cn.itcast.hiss.message.sender.MessageSenderExecuter;
import cn.itcast.hiss.message.sender.MessageSenderManager;
import cn.itcast.hiss.message.sender.impl.DefaultMessageSenderExecuterImpl;
import cn.itcast.hiss.message.sender.properties.MessageSenderAsyncExecutorProperties;
import cn.itcast.hiss.message.sender.properties.MessageSenderProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author miukoo
 * @description 消息发送者自动化配置类
 * @date 2023/5/12 23:52
 * @version 1.0
 **/
@EnableConfigurationProperties({MessageSenderProperties.class, MessageSenderAsyncExecutorProperties.class})
public class MessageSenderAutoConfiguration {


    /**
     * 自定义一个异步线程池，用于执行异步发送的消息任务
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "messageSenderAsyncExecutor")
    public ExecutorService messageSenderAsyncExecutor(MessageSenderAsyncExecutorProperties properties) {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(properties.getThreadNamePrefix());
        return new ThreadPoolExecutor(properties.getCorePoolSize(),
                properties.getMaxPoolSize(), properties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(properties.getQueueSize()),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSenderManager messageSenderManager() {
        return new MessageSenderManager();
    }


    @Bean
    @ConditionalOnMissingBean
    public MessageSenderExecuter messageSenderExecuter() {
        return new DefaultMessageSenderExecuterImpl();
    }


}
