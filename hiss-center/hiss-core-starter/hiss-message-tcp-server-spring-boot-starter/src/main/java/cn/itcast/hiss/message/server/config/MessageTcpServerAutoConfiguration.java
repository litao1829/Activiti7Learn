package cn.itcast.hiss.message.server.config;

import cn.itcast.hiss.cmd.properties.MessageReceiverAsyncExecutorProperties;
import cn.itcast.hiss.message.server.message.MessageSessionFactory;
import cn.itcast.hiss.message.server.perporties.MessageTcpReceiverProperties;
import cn.itcast.hiss.message.server.perporties.MessageTcpServerProperties;
import cn.itcast.hiss.message.server.sender.ServerTcpSender;
import cn.itcast.hiss.message.server.server.HissNettyServer;
import cn.itcast.hiss.message.server.server.HissServerInitializer;
import cn.itcast.hiss.message.server.server.handler.HissServerHeartbeatHandler;
import cn.itcast.hiss.message.server.server.handler.HissServerMessageHandler;
import cn.itcast.hiss.message.server.service.TcpAuthCheckService;
import cn.itcast.hiss.message.server.service.TcpReceiverService;
import cn.itcast.hiss.message.server.service.impl.TcpAuthCheckServiceImpl;
import cn.itcast.hiss.message.server.service.impl.TcpReceiverServiceImpl;
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
@AutoConfigureBefore(name="cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
@EnableConfigurationProperties({MessageTcpReceiverProperties.class, MessageTcpServerProperties.class})
public class MessageTcpServerAutoConfiguration {

    /**
     * 自定义一个异步线程池，用于执行异步发送的消息任务
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name="hissMessageReceiverAsyncExecutor")
    public ExecutorService hissMessageReceiverAsyncExecutor(MessageReceiverAsyncExecutorProperties properties) {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(properties.getThreadNamePrefix()+"server-");
        return new ThreadPoolExecutor(properties.getCorePoolSize(),
                properties.getMaxPoolSize(), properties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(properties.getQueueSize()),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    @Bean
    @ConditionalOnMissingBean(name="hissServerHeartbeatHandler")
    public HissServerHeartbeatHandler hissServerHeartbeatHandler(){
        return new HissServerHeartbeatHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name="hissNettyServer")
    public HissNettyServer hissNettyServer() {
        return new HissNettyServer();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissServerInitializer hissServerInitializer() {
        return new HissServerInitializer();
    }

    @Bean
    @ConditionalOnMissingBean
    public TcpAuthCheckService tcpAuthCheckService() {
        return new TcpAuthCheckServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public TcpReceiverService tcpReceiverService() {
        return new TcpReceiverServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissServerMessageHandler hissServerMessageHandler() {
        return new HissServerMessageHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name="hissMessageSessionFactoryInit")
    public MessageSessionFactory hissMessageSessionFactoryInit() {
        return new MessageSessionFactory();
    }

    @Bean
    @ConditionalOnMissingBean(name="serverTcpSender")
    public ServerTcpSender serverTcpSender() {
        return new ServerTcpSender();
    }
}
