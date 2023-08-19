package cn.itcast.hiss.api.client.processdefinition;

import cn.itcast.hiss.api.client.processdefinition.viewer.*;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description 设计器设计的模型
 * @date 2023/5/25 16:55
 * @version 1.0
 **/
@Data
public class ProcessViewerModel {

    ModelTypeEnum type = ModelTypeEnum.DEV;
    // 流程实例ID
    String processInstanceId;

    // 预览模式，每个节点的高亮情况
    Map<String, List<ViewerNodeInfo>> highlights = new HashMap<>();

    // 当前激活的任务
    List<ActiveNodeInfo> activeTasks = new ArrayList<>();

    // 流程评论
    List<ActHiComment> comments = new ArrayList<>();

    // 主流程中的节点ID
    List<GraphicNodeInfo> nodes = new ArrayList<>();

    // 主流程中的节点ID
    List<ProcessFormInfo> forms = new ArrayList<>();

    // 流程基本信息
    ProcessInfo processInfo;
    // 设计文件附加配置文件
    String configJson;
    // 文件内容
    String content;
}
