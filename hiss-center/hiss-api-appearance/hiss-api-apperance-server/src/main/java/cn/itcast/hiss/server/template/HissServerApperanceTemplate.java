package cn.itcast.hiss.server.template;

import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.api.client.dto.FormServerDefinitionDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.form.FormDefinition;
import cn.itcast.hiss.api.client.form.GetFormDefinition;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageContext;

import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description 暴露的给上层使用的接口类
 * 1、flowM 流程定义相关
 * 2、flowB 流程办理相关
 * @date 2023/5/24 19:00
 * @version 1.0
 **/
public interface HissServerApperanceTemplate {


    public MessageContext eventActivitiProcessNotice(String tenant, HissActivitiEvent payload);


    /**
     * 获取用户信息--用户自定义变量
     *
     * @param customVariable
     * @return
     */
    List<String> getUserInfo(String tenantId, UserInfo customVariable);

    /**
     * 获取流程定义参数
     * @param getFormDefinition
     * @return
     */
    FormServerDefinitionDTO getFormDefinition(GetFormDefinition getFormDefinition);

    /**
     * 获取客户端方法变量
     * @param tenantId
     * @param palyload
     * @return
     */
    List<Map> getClientVariablesMethod(String tenantId, VariableMethod palyload);
}
