package cn.itcast.hiss.api.client.task;

import cn.itcast.hiss.common.enums.ReceivedTypeEnum;
import lombok.Data;

import java.util.Map;

/**
 * ReceivedTask
 *
 * @author: miukoo
 * @describe: 触发任务
 * @date: 2022/12/28 10:10
 */
@Data
public class ReceivedTask {


    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 信号或者代码或者消息名
     */
    private String name;

    /**
     * 流程变量
     */
    private Map<String,Object> variables;

    /**
     * 触发类型
     */
    private ReceivedTypeEnum type;


    /**
     * 操作名称
     */
    private String operatorName;
}
