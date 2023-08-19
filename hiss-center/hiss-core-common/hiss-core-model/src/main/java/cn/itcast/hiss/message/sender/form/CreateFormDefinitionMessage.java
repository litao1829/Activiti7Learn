package cn.itcast.hiss.message.sender.form;

import cn.itcast.hiss.api.client.form.CreateFormDefinition;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * CreateFormDefinitionMessage
 *
 * @author: wgl
 * @describe: 创建表单定义消息类
 * @date: 2022/12/28 10:10
 */
@Data
public class CreateFormDefinitionMessage implements Message<CreateFormDefinition> {
    private static final String ID = HandlerIdClientEnum.FORM_CREATE_FORM_DIFINITION.getId();
    private String id = ID;
    private MessageAuth messageAuth;
    private CreateFormDefinition palyload;
    private MessageConfig messageConfig;
}
