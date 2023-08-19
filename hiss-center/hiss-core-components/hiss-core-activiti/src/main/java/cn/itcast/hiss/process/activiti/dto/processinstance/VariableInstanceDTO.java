package cn.itcast.hiss.process.activiti.dto.processinstance;

import lombok.Data;

/**
 * VariableInstanceDTO
 *
 * @author: wgl
 * @describe: 流程实例参数
 * @date: 2022/12/28 10:10
 */
@Data
public class VariableInstanceDTO {
    private String name;

    private String type;

    private String processInstanceId;

    private String taskId;

    private boolean taskVariable;

    private Object value;
}
