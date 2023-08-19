package cn.itcast.hiss.server.config;

import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import cn.itcast.hiss.server.template.impl.HissServerApperanceTemplateImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * @author miukoo
 * @description 服务端的外观自动转配类
 * @date 2023/5/24 19:44
 * @version 1.0
 **/
@Configuration
@AutoConfigureAfter(name = "cn.itcast.hiss.message.sender.config.MessageSenderAutoConfiguration")
public class HissServerApperanceAutoConfiguration {

    /**
     * 自动配置暴露的客户端API
     */
    @Bean("hissServerApperanceTemplate")
    @ConditionalOnMissingBean
    public HissServerApperanceTemplate hissServerApperanceTemplate() {
        return new HissServerApperanceTemplateImpl();
    }

}
