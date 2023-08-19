package cn.itcast.hiss.api.client.processinstance;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * HissProcessInstance
 *
 * @author: wgl
 * @describe: hiss系统的流程实例
 * @date: 2022/12/28 10:10
 */
@Data
public class HissProcessInstance {

    /**
     * 流程实例id
     */
    private String id;

    /**
     * 流程实例key
     */
    private String key;

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
