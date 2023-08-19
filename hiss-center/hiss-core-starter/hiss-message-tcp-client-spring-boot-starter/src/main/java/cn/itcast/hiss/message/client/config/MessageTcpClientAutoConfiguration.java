package cn.itcast.hiss.message.client.config;

import cn.itcast.hiss.cmd.properties.MessageReceiverAsyncExecutorProperties;
import cn.itcast.hiss.message.client.client.HissNettyClient;
import cn.itcast.hiss.message.client.perporties.MessageTcpClientProperties;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.message.client.sender.MessageTcpSender;
import cn.itcast.hiss.message.client.server.handler.HissClientHeartbeatHandler;
import cn.itcast.hiss.message.client.server.handler.HissClientInitializer;
import cn.itcast.hiss.message.client.server.handler.HissClientMessageHandler;
import cn.itcast.hiss.message.client.service.TcpAuthService;
import cn.itcast.hiss.message.client.service.TcpSenderService;
import cn.itcast.hiss.message.client.service.impl.TcpAuthServiceImpl;
import cn.itcast.hiss.message.client.service.impl.TcpSenderServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
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
 * @description 在发送器启动后，自动转配http发送器
 * @date 2023/5/13 15:40
 * @version 1.0
 **/
@AutoConfigureBefore(name = "cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
@EnableConfigurationProperties({MessageTcpSenderProperties.class, MessageTcpClientProperties.class})
public class MessageTcpClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MessageTcpSender messageTcpSender() {
        return new MessageTcpSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissClientInitializer hissClientInitializer() {
        return new HissClientInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public TcpAuthService tcpAuthService() {
        return new TcpAuthServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public TcpSenderService tcpSenderService() {
        return new TcpSenderServiceImpl();
    }


    @Bean
    @ConditionalOnMissingBean
    public HissClientHeartbeatHandler hissClientHeartbeatHandler() {
        return new HissClientHeartbeatHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissNettyClient hissNettyClient() {
        return new HissNettyClient();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissClientMessageHandler hissClientMessageHandler() {
        return new HissClientMessageHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name="hissMessageReceiverAsyncExecutor")
    public ExecutorService hissMessageReceiverAsyncExecutor(MessageReceiverAsyncExecutorProperties properties) {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(properties.getThreadNamePrefix()+"client-");
        return new ThreadPoolExecutor(properties.getCorePoolSize(),
                properties.getMaxPoolSize(), properties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(properties.getQueueSize()),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
