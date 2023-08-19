package cn.itcast.hiss.api.client.processdefinition;

import lombok.Data;

/**
 * DeleteProcessDefinition
 *
 * @author: wgl
 * @describe: 删除流程定义
 * @date: 2022/12/28 10:10
 */
@Data
public class DeleteProcessDefinition {

    /**
     * 流程定义ID
     */
    private String processID;

    /**
     * 是否删除历史数据标志位
     */
    private boolean historyDelFlag;

}
