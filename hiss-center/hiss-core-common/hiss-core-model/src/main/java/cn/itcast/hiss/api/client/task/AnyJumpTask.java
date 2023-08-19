package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.Map;

/**
 * AnyJumpTask
 *
 * @author: miukoo
 * @describe: 任意跳转
 * @date: 2022/12/28 10:10
 */
@Data
public class AnyJumpTask {

    /**
     * 驳回、跳转、撤回
     */
    private String operatorName="";

    /**
     * 当前任务ID
     */
    private String taskId;

    /**
     * 回退任务节点id
     */
    private String jumpTaskOrActivityId;

    /**
     * 回退原因
     */
    private String reason;

    /**
     * 其他参数
     */
    private Map<String,Object> variables;

    /**
     * 回退类型
     */
    private RejectType rejectType;

}
