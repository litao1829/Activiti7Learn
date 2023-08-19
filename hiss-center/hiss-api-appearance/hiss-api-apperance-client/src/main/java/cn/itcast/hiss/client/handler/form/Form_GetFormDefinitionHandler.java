package cn.itcast.hiss.client.handler.form;

import cn.itcast.hiss.api.client.form.FormDefinition;
import cn.itcast.hiss.api.client.form.GetFormDefinition;
import cn.itcast.hiss.client.service.form.FormDefinitionService;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.form.GetFormDefinitionByIdMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Form_GetFormDefinitionHandler
 *
 * @author: wgl
 * @describe: 根据表单定义id获取表单定义信息
 * @date: 2022/12/28 10:10
 */
@Component
public class Form_GetFormDefinitionHandler implements CmdHandler<GetFormDefinitionByIdMessage> {


    @Autowired(required = false)
    @Lazy
    private FormDefinitionService formDefinitionService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        GetFormDefinition getInfo = (GetFormDefinition) params.getPalyload();
        FormDefinition formDefinition = formDefinitionService.getFormDefinition(getInfo.getIds(), getInfo.getQuery());
        messageContext.setResult(new ConcurrentHashMap<>(1) {
            {
                put("res", formDefinition);
            }
        });
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.FROM_CLIENT_GET_FROM_DIFINITION.getId();
    }
}
