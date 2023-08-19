package cn.itcast.hiss.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HandlerIdServerEnum
 *
 * @author: wgl
 * @describe: 服务端handlerId枚举
 * @date: 2022/12/28 10:10
 */
@AllArgsConstructor
@Getter
public enum HandlerIdServerEnum {
    DIRECT_LEADER("HisDirectLeader","获取直属领导"),

    DIRECT_DEPARTMENT("HissDirectDepartment","直属部门领导"),

    DESIGNATED_ROLE("designatedRole","指定角色"),

    DESIGNATED_DEPARTMENT("HissDesignateDepartment","指定部门"),

    CUSTOM_VARIABLES("CustomVariables","自定义变量"),

    EVENT_ACTIVITI_PROCESS_NOTICE("EVENT_ACTIVITI_PROCESS_NOTICE","通知客户端消息事件")
    ;

    private String id;

    private String name;
}
