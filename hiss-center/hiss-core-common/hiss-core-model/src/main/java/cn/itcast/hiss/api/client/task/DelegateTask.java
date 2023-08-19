package cn.itcast.hiss.api.client.task;

import lombok.Data;

/**
 * DelegateTask
 *
 * @author: wgl
 * @describe: 交办任务
 * @date: 2022/12/28 10:10
 */
@Data
public class DelegateTask {
    /**
     * 委派
     */
    private String operatorName="委派";

    /**
     * 交办任务id
     */
    private String taskId;

    /**
     * 交办任务的目标用户id
     */
    private String userId;

    /**
     * 交办任务的目标用户名
     */
    private String userName;

}
