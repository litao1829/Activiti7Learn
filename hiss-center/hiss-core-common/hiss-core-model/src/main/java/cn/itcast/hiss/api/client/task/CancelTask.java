package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.Map;

/**
 * CancelTask
 *
 * @author: miukoo
 * @describe: 取消任务
 * @date: 2022/12/28 10:10
 */
@Data
public class CancelTask {

    /**
     * 取消
     */
    private String operatorName="";

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 取消原因
     */
    private String reason;

    /**
     * 任务参数
     */
    private Map<String,Object> variables;

}
