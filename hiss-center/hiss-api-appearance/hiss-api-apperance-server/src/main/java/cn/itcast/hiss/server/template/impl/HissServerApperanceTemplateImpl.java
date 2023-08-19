package cn.itcast.hiss.server.template.impl;

import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.api.client.dto.FormServerDefinitionDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.form.FormDefinition;
import cn.itcast.hiss.api.client.form.GetFormDefinition;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.event.mess.HissActivitiEventMessage;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageBuilder;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.MessageSenderExecuter;
import cn.itcast.hiss.message.sender.common.UserInfoMessage;
import cn.itcast.hiss.message.sender.common.VariableMethodMessage;
import cn.itcast.hiss.message.sender.form.GetFormDefinitionByIdMessage;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author miukoo
 * @description 统一处理流程服务端回调客户端相关
 * @date 2023/5/24 19:43
 * @version 1.0
 **/
public class HissServerApperanceTemplateImpl implements HissServerApperanceTemplate {

    @Autowired
    MessageSenderExecuter messageSenderExecuter;


    /**
     * 通知客户端信息
     *
     * @param payload
     */
    public MessageContext eventActivitiProcessNotice(String tenetId, HissActivitiEvent payload) {
        Message message = MessageBuilder.builder().id(HissActivitiEventMessage.ID).palyload(payload).tenant(tenetId)
                .build(HissActivitiEventMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取用户信息，用户自定义
     *
     * @param tenetId
     * @param payload
     * @return
     */
    @Override
    public List<String> getUserInfo(String tenetId, UserInfo payload) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CUSTOM_VARIABLES.getId()).palyload(payload).tenant(tenetId)
                .build(UserInfoMessage.class);
        ConcurrentHashMap<String, Object> result = messageSenderExecuter.sendMessage(message).getResult();
        return result.get("list") != null ? (List<String>) result.get("list") : null;
    }

    /**
     * 获取表单定义
     * @param getFormDefinition
     */
    @Override
    public FormServerDefinitionDTO getFormDefinition(GetFormDefinition getFormDefinition) {
        //TODO 未实现先写死租户为hiss_tenet
        String tenantId = "hiss_tenet";
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CUSTOM_VARIABLES.getId()).palyload(getFormDefinition).tenant(tenantId)
                .build(GetFormDefinitionByIdMessage.class);
        ConcurrentHashMap<String, Object> result = messageSenderExecuter.sendMessage(message).getResult();
        return result.get("res") != null ? (FormServerDefinitionDTO) result.get("res") : null;
    }


    @Override
    public List<Map> getClientVariablesMethod(String tenantId, VariableMethod palyload) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CUSTOM_VARIABLES_METHOD.getId()).palyload(palyload).tenant(tenantId)
                .build(VariableMethodMessage.class);
        ConcurrentHashMap<String, Object> result = messageSenderExecuter.sendMessage(message).getResult();
        return result.get("flag") != null ? (List<Map>) result.get("flag") : null;
    }
}
