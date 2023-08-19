package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.RejectTask;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * RejectTaskMessage
 *
 * @author: wgl
 * @describe: 拒绝消息
 * @date: 2022/12/28 10:10
 */
@Data
public class RejectTaskMessage implements Message<RejectTask> {

    private final static String ID = HandlerIdClientEnum.TASK_REJECT_TASK.getId();

    private String id = ID;

    private MessageAuth messageAuth;

    private RejectTask palyload;

    private MessageConfig messageConfig;
}
