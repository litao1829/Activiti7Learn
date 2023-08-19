package cn.itcast.hiss.cmd.config;

import cn.itcast.hiss.cmd.CmdExecuter;
import cn.itcast.hiss.cmd.MessageChainsManager;
import cn.itcast.hiss.cmd.MessageReceiverHandlerManager;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.cmd.impl.CmdExecuterImpl;
import cn.itcast.hiss.cmd.interceptor.AuthPreInterceptor;
import cn.itcast.hiss.cmd.interceptor.CmdInvoker;
import cn.itcast.hiss.cmd.interceptor.LogInterceptor;
import cn.itcast.hiss.cmd.properties.MessageReceiverAsyncExecutorProperties;
import cn.itcast.hiss.cmd.properties.MessageReceiverProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * MessageReceiverAutoConfiguration
 *
 * @author: wgl
 * @describe: 消息接收者自动化配置类
 * @date: 2022/12/28 10:10
 */
@EnableConfigurationProperties({MessageReceiverProperties.class, MessageReceiverAsyncExecutorProperties.class})
public class MessageReceiverAutoConfiguration {

    /**
     * 自定义一个异步线程池，用于执行异步发送的消息任务
     *
     * @param properties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name="interceptorExecutor")
    public ExecutorService interceptorExecutor(MessageReceiverAsyncExecutorProperties properties) {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory(properties.getThreadNamePrefix());
        return new ThreadPoolExecutor(properties.getCorePoolSize(),
                properties.getMaxPoolSize(), properties.getKeepAliveTime(),
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(properties.getQueueSize()),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 项目启动进行默认的类加载--加载所有的接受处理器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageReceiverHandlerManager messageReceiverHandlerManager() {
        return new MessageReceiverHandlerManager();
    }

    /**
     * 项目启动进行默认的扫描所有过滤器链
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageChainsManager messageChainsManager() {
        return new MessageChainsManager();
    }


    /**
     * 初始化系统默认权限拦截器
     *
     * @return
     */
    @Bean("CmdAuthInterceptor")
    @ConditionalOnMissingBean
    public AuthPreInterceptor initAuthInterceptor() {
        return new AuthPreInterceptor();
    }

    /**
     * 初始化Cmd拦截器（用来打通handler）
     *
     * @return
     */
    @Bean("CmdInvoker")
    @ConditionalOnMissingBean
    public CmdInvoker initCmdInterceptor() {
        return new CmdInvoker();
    }

    /**
     * 初始化日志拦截器
     *
     * @return
     */
    @Bean("LogInterceptor")
    @ConditionalOnMissingBean
    public LogInterceptor initLogInterceptor() {
        return new LogInterceptor();
    }


    @Bean
    @ConditionalOnMissingBean
    public CmdExecuter initCmdExecuter() {
        return new CmdExecuterImpl();
    }


//    /**
//     * 初始化异步配置类
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean
//    public MessageReceiverAsyncExecutorProperties initReceiverAsyncPerporties() {
//        return new MessageReceiverAsyncExecutorProperties();
//    }

    /**
     * 初始化异步配置类
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageReceiverProperties initReceiverPerporties() {
        return new MessageReceiverProperties();
    }
}
