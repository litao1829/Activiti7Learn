package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.TaskAssigned;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * TaskAssignedMessage
 *
 * @author: wgl
 * @describe: 任务指派, 归还，交办消息
 * @date: 2022/12/28 10:10
 */
@Data
public class TaskAssignedMessage implements Message<TaskAssigned> {
    private String id = HandlerIdClientEnum.TASK_ASSIGNED_TASK.getId();
    private MessageAuth messageAuth;
    private TaskAssigned palyload;
    private MessageConfig messageConfig;
}
