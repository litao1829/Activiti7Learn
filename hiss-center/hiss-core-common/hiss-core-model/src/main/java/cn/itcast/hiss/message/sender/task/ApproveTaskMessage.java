package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.ApproveTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * ApproveTaskMessage
 *
 * @author: wgl
 * @describe: 同意任务发送的消息
 * @date: 2022/12/28 10:10
 */
@Data
public class ApproveTaskMessage implements Message<ApproveTask> {

    private final static String ID = HandlerIdClientEnum.TASK_APPROVE_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private ApproveTask palyload;

    private MessageConfig messageConfig;
}
