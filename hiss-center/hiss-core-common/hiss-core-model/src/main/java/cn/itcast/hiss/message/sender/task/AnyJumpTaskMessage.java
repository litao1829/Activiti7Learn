package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.AnyJumpTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * AnyJumpTaskMessage
 *
 * @author: miukoo
 * @describe: 任意跳转对象，支持驳回、跳转、撤回、回退
 * @date: 2022/12/28 10:10
 */
@Data
public class AnyJumpTaskMessage implements Message<AnyJumpTask> {
    private final static String ID = HandlerIdClientEnum.TASK_ROLLBACK_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private AnyJumpTask palyload;

    private MessageConfig messageConfig;
}
