package cn.itcast.hiss.client.config;

import cn.itcast.hiss.client.manager.HissSubmitHandlerManager;
import cn.itcast.hiss.client.manager.HissVariableHandlerManager;
import cn.itcast.hiss.client.scan.HissVariableHandlerScan;
import cn.itcast.hiss.client.submit.items.VaribaleMessage;
import cn.itcast.hiss.client.template.HissClientApperanceTemplate;
import cn.itcast.hiss.client.template.impl.DefaultHissClientApperanceTemplate;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 客户端的外观类
 *
 * @author: wgl
 * @describe: 发送消息的自动配置类
 * @date: 2022/12/28 10:10
 */
@Configuration
@AutoConfigureAfter(name = "cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
@ComponentScan(value={"cn.itcast.hiss.client.handler","cn.itcast.hiss.client.handler.sys.methods"})
public class HissClientApperanceAutoConfiguration {

    /**
     * 自动配置暴露的客户端API
     */
    @Bean
    @ConditionalOnMissingBean
    public HissClientApperanceTemplate hissClientApperanceTemplate() {
        return new DefaultHissClientApperanceTemplate();
    }

    @Bean
    @ConditionalOnMissingBean
    public HissVariableHandlerScan hissSpelHandlerScan() {
        return new HissVariableHandlerScan();
    }

    /**
     * 变量管理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HissVariableHandlerManager hissSpelHandlerManager() {
        return new HissVariableHandlerManager();
    }

    /**
     * 上报管理器
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HissSubmitHandlerManager submitHandlerManager(){
        return new HissSubmitHandlerManager();
    }

    @Bean
    @ConditionalOnMissingBean
    public VaribaleMessage varibaleMessage(){
        return new VaribaleMessage();
    }
}
