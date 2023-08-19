package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.AddExecutionTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * AddExecutionTaskMessage
 *
 * @author: wgl
 * @describe: 加签任务消息
 * @date: 2022/12/28 10:10
 */
@Data
public class AddExecutionTaskMessage implements Message<AddExecutionTask> {

    private String id;

    private MessageAuth messageAuth;

    private AddExecutionTask palyload;

    private MessageConfig messageConfig;
}