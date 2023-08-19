package cn.itcast.hiss.event.pojo;

import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description 通知到客户端的事件信息
 * @date 2023/5/26 13:09
 * @version 1.0
 **/
@Data
public class HissActivitiEvent {

    // 事件名称
    String eventName;
    // 当前流程实例Id
    String processInstanceId;
    // 当前流程定义Id
    String processDefinitionId;
    // 当前执行步骤Id
    String executionId;
    // 执行的className、signalName等
    String targetName;
    // 操作类型，用于区分客户端分发的类型
    EventOperationTypeEnum operationType = EventOperationTypeEnum.EXECUTE_CLASS;
    // 定义的字段信息
    List<Field> fields;
    // 全局变量
    Map<String, Object> variables;
    // 本地变量
    Map<String, Object> variablesLocal;
    // 自定义属性集合
    Map<String, Object> customPropertiesMap;
    // 流程实例上的业务关键key
    String businessKey;
    // 如果是事件，这里存储的是事件的数据
    Object eventData;


}
