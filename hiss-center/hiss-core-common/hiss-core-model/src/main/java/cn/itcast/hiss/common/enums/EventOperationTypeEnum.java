package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @author miukoo
 * @description 事件分发的操作类型
 * @date 2023/5/26 15:07
 * @version 1.0
 **/
@AllArgsConstructor
@Getter
public enum EventOperationTypeEnum {

    SEND_MAIL_EVENT_NOTICE("远程发送邮件通知"),
    GLOBAL_EVENT_NOTICE("远程全局事件通知"),
    ERROR_CODE_EVENT_NOTICE("抛出远程错误事件通知"),
    MESSAGE_EVENT_NOTICE("抛出远程消息通知"),
    SIGNAL_EVENT_NOTICE("抛出远程信号通知"),
    EXECUTE_CLASS("远程执行CLASS");

    private String name;

}
