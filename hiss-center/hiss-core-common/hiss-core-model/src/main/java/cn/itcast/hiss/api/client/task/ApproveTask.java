package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.Map;

/**
 * AgreeTask
 *
 * @author: wgl
 * @describe: 通过任务
 * @date: 2022/12/28 10:10
 */
@Data
public class ApproveTask {

    /**
     * 操作名，或者按钮名，用于不同前端的渲染显示
     */
    private String operatorName="同意";

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 任务参数
     */
    private Map<String,Object> variables;

    /**
     * 评论
     */
    private String reason;

    /**
     * 表单ID
     */
    private String formId;

    /**
     * 数据ID
     */
    private String dataId;

}
