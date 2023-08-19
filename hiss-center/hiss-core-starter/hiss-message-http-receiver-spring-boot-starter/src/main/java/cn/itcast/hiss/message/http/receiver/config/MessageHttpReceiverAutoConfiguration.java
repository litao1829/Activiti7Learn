package cn.itcast.hiss.message.http.receiver.config;

import cn.itcast.hiss.message.http.receiver.controller.MessageHttpReceiverController;
import cn.itcast.hiss.message.http.receiver.properties.MessageHttpReceiverProperties;
import cn.itcast.hiss.message.http.receiver.properties.MessageHttpSenderProperties;
import cn.itcast.hiss.message.http.receiver.sender.ServerHttpSender;
import cn.itcast.hiss.message.http.receiver.sender.service.HttpAuthService;
import cn.itcast.hiss.message.http.receiver.sender.service.impl.HttpAuthServiceImpl;
import cn.itcast.hiss.message.http.receiver.service.HttpAuthCheckService;
import cn.itcast.hiss.message.http.receiver.service.HttpReceiverService;
import cn.itcast.hiss.message.http.receiver.service.impl.HttpAuthCheckServiceImpl;
import cn.itcast.hiss.message.http.receiver.service.impl.HttpReceiverServiceImpl;
import cn.itcast.hiss.message.sender.Sender;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/*
 * @author miukoo
 * @description 在发送器启动后，自动转配http发送器
 * @date 2023/5/13 15:40
 * @version 1.0
 **/
@AutoConfigureAfter(name = "cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
@EnableConfigurationProperties(MessageHttpReceiverProperties.class)
public class MessageHttpReceiverAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public HttpAuthCheckService httpAuthCheckService() {
        return new HttpAuthCheckServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpReceiverService httpReceiverService() {
        return new HttpReceiverServiceImpl();
    }


    /**
     * 内置controller加载
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public MessageHttpReceiverController initMessageHttpReceiverController() {
        return new MessageHttpReceiverController();
    }


    @Bean("hissServerHttpAuthServiceInit")
    @ConditionalOnMissingBean
    public HttpAuthService hissServerHttpAuthServiceInit() {
        return new HttpAuthServiceImpl();
    }

    @Bean("hissServerMessageHttpSenderPropertiesInit")
    @ConditionalOnMissingBean
    public MessageHttpSenderProperties hissServerMessageHttpSenderPropertiesInit() {
        return new MessageHttpSenderProperties();
    }

    @Bean("hissMessageHttpSenderInit")
    @ConditionalOnMissingBean
    public Sender serverSender() {
        return new ServerHttpSender();
    }
}
