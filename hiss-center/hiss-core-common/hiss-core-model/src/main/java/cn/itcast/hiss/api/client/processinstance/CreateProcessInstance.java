package cn.itcast.hiss.api.client.processinstance;

import lombok.Data;

import java.util.Map;

/**
 * CreateProcessInstance
 *
 * @author: wgl
 * @describe: 创建流程实例
 * @date: 2022/12/28 10:10
 */
@Data
public class CreateProcessInstance {
    /**
     * 流程定义id
     */
    private String processDefinitionId;

    /**
     * 流程实例key
     */
    private String bussinessKey;

    /**
     * 流程实例名称
     */
    private String name;

    /**
     * 版本号
     */
    private int version;

    /**
     * 流程变量
     */
    private Map<String,Object> variables;
}
