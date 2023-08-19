package cn.itcast.hiss.message.sender.processinstance;

import cn.itcast.hiss.api.client.processinstance.CreateProcessInstance;
import cn.itcast.hiss.api.client.processinstance.HissProcessInstance;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * CreateProcessInstanceMessage
 *
 * @author: wgl
 * @describe: 创建流程实例消息对象
 * @date: 2022/12/28 10:10
 */
@Data
public class CreateProcessInstanceMessage implements Message<CreateProcessInstance> {
    private String id;
    private MessageAuth messageAuth;
    private CreateProcessInstance palyload;
    private MessageConfig messageConfig;
}
