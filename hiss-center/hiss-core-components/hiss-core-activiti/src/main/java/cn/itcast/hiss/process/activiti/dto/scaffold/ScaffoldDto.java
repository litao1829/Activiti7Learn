package cn.itcast.hiss.process.activiti.dto.scaffold;

import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description 若依导出脚手架配置类
 * @date 2023/7/13 16:22
 * @version 1.0
 **/
@Data
public class ScaffoldDto {

    // 框架名称
    String name="RuoYi-Vue";
    // 模板名称
    String templateName="ruoyi";
    String host;
    String httpPort;
    String tcpPort;
    String appId;
    String version;
    String type;
    String sql;
    List<Map> contents;

}
