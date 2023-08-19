package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.common.enums.ActiveNodeTypeEnum;
import lombok.Data;

import java.util.Map;

/*
 * @author miukoo
 * @description 流程节点，激活节点的信息
 * @date 2023/5/31 16:02
 * @version 1.0
 **/
@Data
public class ActiveNodeInfo {
    // 任务id
    String taskId;
    // 审批人
    String assignee;
    // 活动类型
    String activityType;
    // 多实例类型
    ActiveNodeTypeEnum nodeType;
    // 图节点ID
    String nodeId;
    // 可选候选人
    Map<String,String> candidateUser;
}
