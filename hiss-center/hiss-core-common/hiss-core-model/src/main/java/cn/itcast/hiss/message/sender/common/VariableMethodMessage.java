package cn.itcast.hiss.message.sender.common;

import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * VariableMethodMessage
 *
 * @author: wgl
 * @describe: 服务变量消息方法
 * @date: 2022/12/28 10:10
 */
@Data
public class VariableMethodMessage implements Message<VariableMethod> {
    private String id;
    private MessageAuth messageAuth;
    private VariableMethod palyload;
    private MessageConfig messageConfig;
}
