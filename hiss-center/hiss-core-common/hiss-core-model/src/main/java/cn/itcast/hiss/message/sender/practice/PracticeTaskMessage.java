package cn.itcast.hiss.message.sender.practice;

import cn.itcast.hiss.api.client.task.PracticeTask;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

@Data
public class PracticeTaskMessage implements Message<PracticeTask> {

    private String id;

    private MessageAuth messageAuth;

    private PracticeTask palyload;

    private MessageConfig messageConfig;
}
