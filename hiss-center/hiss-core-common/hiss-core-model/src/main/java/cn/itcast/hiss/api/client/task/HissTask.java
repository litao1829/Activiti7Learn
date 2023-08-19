package cn.itcast.hiss.api.client.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * HissTask
 *
 * @author: wgl
 * @describe: 任务
 * @date: 2022/12/28 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HissTask {

    /**
     * 认领、归还、催办等操作
     */
    private String operatorName="";

    private String taskId;

    private String processInstanceId;

    private Map<String,Object> variables;
}
