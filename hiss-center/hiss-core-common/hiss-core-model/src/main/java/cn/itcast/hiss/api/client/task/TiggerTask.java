package cn.itcast.hiss.api.client.task;

import lombok.Data;

import java.util.Map;

/**
 * SignTask
 *
 * @author: wgl
 * @describe: 信化任务
 * @date: 2022/12/28 10:10
 */
@Data
public class TiggerTask {


    /**
     * 执行id
     */
    private String executionId;

    /**
     * 流程变量
     */
    private Map<String,Object> variables;


    /**
     * 操作名称
     */
    private String operatorName;
}
