package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.ReceivedTask;
import cn.itcast.hiss.api.client.task.TiggerTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * ReceivedTaskMessage
 *
 * @author: miukoo
 * @describe: 触发任务
 * @date: 2022/12/28 10:10
 */
@Data
public class ReceivedTaskMessage implements Message<ReceivedTask> {

    private final static String ID = HandlerIdClientEnum.TASK_RECEIVED_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private ReceivedTask palyload;

    private MessageConfig messageConfig;

}
