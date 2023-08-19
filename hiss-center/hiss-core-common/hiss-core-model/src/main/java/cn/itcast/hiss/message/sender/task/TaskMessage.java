package cn.itcast.hiss.message.sender.task;

import cn.itcast.hiss.api.client.task.HissTask;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * TaskMessage
 *
 * @author: wgl
 * @describe: 任务
 * @date: 2022/12/28 10:10
 */
@Data
public class TaskMessage implements Message<HissTask> {
    private String id;
    private MessageAuth messageAuth;
    private HissTask palyload;
    private MessageConfig messageConfig;
}
