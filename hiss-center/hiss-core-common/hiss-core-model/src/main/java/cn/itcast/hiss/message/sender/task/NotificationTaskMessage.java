package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.NotificationTask;
import cn.itcast.hiss.api.client.task.TaskAssigned;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 *
 * @author: miukoo
 * @describe: 知会
 * @date: 2022/12/28 10:10
 */
@Data
public class NotificationTaskMessage implements Message<NotificationTask> {
    private String id = HandlerIdClientEnum.TASK_NOTIFICATION_TASK.getId();
    private MessageAuth messageAuth;
    private NotificationTask palyload;
    private MessageConfig messageConfig;
}
