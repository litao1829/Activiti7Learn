package cn.itcast.hiss.api.client.task;

import lombok.Data;

/**
 * TaskAssigned
 *
 * @author: wgl
 * @describe: 任务指派
 * @date: 2022/12/28 10:10
 */
@Data
public class TaskAssigned {

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 指派人
     */
    private String assigned;
}
