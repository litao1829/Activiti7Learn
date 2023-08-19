package cn.itcast.hiss.message.sender.processinstance;

import cn.itcast.hiss.api.client.processinstance.HissProcessInstance;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * HissProcessInstanceMessage
 *
 * @author: wgl
 * @describe: 流程实例消息
 * @date: 2022/12/28 10:10
 */
@Data
public class HissProcessInstanceMessage implements Message<HissProcessInstance> {
    private String id;
    private MessageAuth messageAuth;
    private HissProcessInstance palyload;
    private MessageConfig messageConfig;
}
