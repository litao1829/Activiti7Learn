package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.DelegateTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * DelegateTask
 *
 * @author: wgl
 * @describe: 交办任务
 * @date: 2022/12/28 10:10
 */
@Data
public class DelegateTaskMessage implements Message<DelegateTask> {
    private final static String ID = HandlerIdClientEnum.TASK_DELEGATE_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private DelegateTask palyload;

    private MessageConfig messageConfig;
}