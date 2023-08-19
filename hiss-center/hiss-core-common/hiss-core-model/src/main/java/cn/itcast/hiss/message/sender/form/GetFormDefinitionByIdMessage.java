package cn.itcast.hiss.message.sender.form;

import cn.itcast.hiss.api.client.form.GetFormDefinition;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * GetFormDefinitionByIdMessage
 *
 * @author: wgl
 * @describe: 获取表单定义的消息
 * @date: 2022/12/28 10:10
 */
@Data
public class GetFormDefinitionByIdMessage implements Message<GetFormDefinition> {
    private String id;
    private MessageAuth messageAuth;
    private GetFormDefinition palyload;
    private MessageConfig messageConfig;
}
