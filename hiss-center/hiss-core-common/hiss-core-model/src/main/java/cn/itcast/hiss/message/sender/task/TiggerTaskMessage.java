package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.TiggerTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * SignTaskMessage
 *
 * @author: wgl
 * @describe: 信号任务标志位
 * @date: 2022/12/28 10:10
 */
@Data
public class TiggerTaskMessage implements Message<TiggerTask> {

    private final static String ID = HandlerIdClientEnum.TASK_TIGGER_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private TiggerTask palyload;

    private MessageConfig messageConfig;

}
