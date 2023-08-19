package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.Map;

/**
 * RejectTask
 *
 * @author: wgl
 * @describe: 拒绝任务
 * @date: 2022/12/28 10:10
 */
@Data
public class RejectTask {
    /**
     * 操作名，或者按钮名，用于不同前端的渲染显示
     */
    private String operatorName="不同意";

    /**
     * 拒绝任务的ID
     */
    private String taskId;

    /**
     * 拒绝原因
     */
    private String reason;

    /**
     * 其他参数
     */
    private Map<String,Object> variables;
}
