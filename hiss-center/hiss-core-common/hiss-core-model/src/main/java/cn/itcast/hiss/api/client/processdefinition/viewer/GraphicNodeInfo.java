package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.common.enums.ProcessNodeStatusEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 图中流程节点信息
 * @date 2023/5/31 16:02
 * @version 1.0
 **/
@Data
public class GraphicNodeInfo {
    // 状态
    ProcessNodeStatusEnum status = ProcessNodeStatusEnum.DEFAULT;
    // 名称
    String name;
    // id
    String id;
    // 任务id
    String taskId;
    // 办理人
    String assignee;
    // 活动类型
    String activityType;

}
