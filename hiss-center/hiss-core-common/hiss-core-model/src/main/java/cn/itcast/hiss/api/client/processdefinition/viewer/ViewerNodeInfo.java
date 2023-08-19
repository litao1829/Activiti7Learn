package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.common.enums.ProcessNodeStatusEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 流程节点，预览的样式信息
 * @date 2023/5/31 16:02
 * @version 1.0
 **/
@Data
public class ViewerNodeInfo {
    // 状态
    ProcessNodeStatusEnum status = ProcessNodeStatusEnum.DEFAULT;
    // 提示标题
    String tipTitle;
    // 提示内容
    String tipContent;
    // 提示内容
    ViewerTipInfo tipObject;
    // 任务id
    String taskId;
    // 任务在图中的节点名称
    String nodeName;
    // 任务在图中的节点id
    String nodeId;
    // 办理人
    String assignee;
    // 活动类型
    String activityType;
}
