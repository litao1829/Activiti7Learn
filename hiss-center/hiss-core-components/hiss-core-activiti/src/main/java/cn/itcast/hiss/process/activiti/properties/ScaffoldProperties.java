package cn.itcast.hiss.process.activiti.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/*
 * @author miukoo
 * @description 脚手架相关配置类
 * @date 2023/5/13 15:32
 * @version 1.0
 **/
@ConfigurationProperties(prefix = "hiss.scaffold")
@Data
public class ScaffoldProperties {

    /**
     * 获取每个租户的邮件服务器地址
     */
    Map<String, ScaffoldInfo> systems;

}
