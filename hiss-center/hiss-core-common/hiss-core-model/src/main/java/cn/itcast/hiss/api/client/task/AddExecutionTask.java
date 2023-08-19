package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * AddExecutionTask
 *
 * @author: wgl
 * @describe: 加签任务
 * @date: 2022/12/28 10:10
 */
@Data
public class AddExecutionTask {

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 加签人员id
     */
    private String userId;

    /**
     * 加签人员名称
     */
    private String userName;

    /**
     * 是否是前加签
     */
    private Map variables;

    /**
     * 操作类型（不必填）
     */
    private String operatorName;
}
