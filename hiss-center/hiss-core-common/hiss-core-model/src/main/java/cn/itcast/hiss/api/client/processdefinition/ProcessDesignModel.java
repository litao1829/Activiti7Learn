package cn.itcast.hiss.api.client.processdefinition;

import cn.itcast.hiss.common.enums.ModelTypeEnum;
import lombok.Data;

import java.util.Map;

/*
 * @author miukoo
 * @description 设计器设计的模型
 * @date 2023/5/25 16:55
 * @version 1.0
 **/
@Data
public class ProcessDesignModel {

    ModelTypeEnum type = ModelTypeEnum.DEV;
    // 流程实例ID
    String processInstanceId;
    // 模型id，用于更新
    String modelId;
    // 设计文件内容
    String content;
    // 设计文件附加配置文件
    String configJson;
    // 名称
    String name;
    // 名称
    String icon;
    // 类型
    String category;

    String description;

    // 管理的业务类型
    String businessKey;
    /**
     * 其他参数
     */
    private Map<String,Object> variables;
}
