package cn.itcast.hiss.message.http.sender.config;

import cn.itcast.hiss.message.http.sender.properties.MessageHttpSenderProperties;
import cn.itcast.hiss.message.http.sender.sender.MessageHttpSender;
import cn.itcast.hiss.message.http.sender.service.HttpAuthService;
import cn.itcast.hiss.message.http.sender.service.HttpSenderService;
import cn.itcast.hiss.message.http.sender.service.impl.HttpAuthServiceImpl;
import cn.itcast.hiss.message.http.sender.service.impl.HttpSenderServiceImpl;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/*
 * @author miukoo
 * @description 在发送器启动后，自动转配http发送器
 * @date 2023/5/13 15:40
 * @version 1.0
 **/
@AutoConfigureBefore(name="cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
@EnableConfigurationProperties(MessageHttpSenderProperties.class)
public class MessageHttpSenderAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MessageHttpSender messageHttpSender(){
        return new MessageHttpSender();
    }

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate httpRestTemplate(){
        return new RestTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpAuthService httpAuthService(){
        return new HttpAuthServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpSenderService httpSenderService(){
        return new HttpSenderServiceImpl();
    }

}
