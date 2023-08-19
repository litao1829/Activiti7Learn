package cn.itcast.hiss.process.activiti.listener;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.common.enums.HissActivitiEventTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 全局的事件向客户端分发器
 * @date 2023/5/28 12:29
 * @version 1.0
 **/
@Slf4j
@Component
public class GlobalEventListener implements ActivitiEventListener {

    @Autowired
    HissServerApperanceTemplate hissServerApperanceTemplate;
    boolean failOnException = false;
    RepositoryService repositoryService;
    RuntimeService runtimeService;

    @Override
    public void onEvent(ActivitiEvent event) {
        if(StrUtil.isNotEmpty(event.getProcessDefinitionId())) {
            // 只有类中定义的事件，才传递到客户端
            HissActivitiEventTypeEnum clientType = HissActivitiEventTypeEnum.getTypesFromString(event.getType().name());
            if(clientType!=null) {
                CommandContext commandContext = Context.getCommandContext();
                if(repositoryService==null) {
                    repositoryService = commandContext.getProcessEngineConfiguration().getRepositoryService();
                    runtimeService = commandContext.getProcessEngineConfiguration().getRuntimeService();
                }
                HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder()
                        .eventName(clientType.name())
                        .activitiEvent(event)
                        .build();
                hissActivitiEvent.setOperationType(EventOperationTypeEnum.GLOBAL_EVENT_NOTICE);
                String tenantId = getTenantId(event, hissActivitiEvent);
                if (StrUtil.isEmpty(tenantId)) {
                    System.out.println("====tenantId为空=========" + JSON.toJSONString(event));
                } else {
                    notifyClient(tenantId, hissActivitiEvent);
                }
            }
        }else{
            log.info("全局事件{}:{},，没有定义ID,不推送到客户端~",event.getType(),event.getClass().getName());
        }
    }

    public String getTenantId(ActivitiEvent event,HissActivitiEvent hissActivitiEvent){
        if(event instanceof ActivitiEntityEventImpl){
            ActivitiEntityEventImpl entity = (ActivitiEntityEventImpl) event;
            Object object = entity.getEntity();
            if(object instanceof ProcessDefinitionEntity){
                ProcessDefinitionEntity temp = (ProcessDefinitionEntity) object;
                return temp.getTenantId();
            }
            if(object instanceof IdentityLinkEntityImpl){
                IdentityLinkEntityImpl temp =(IdentityLinkEntityImpl) object;
                ProcessDefinitionEntity processDef = temp.getProcessDef();
                if(processDef!=null){
                    return processDef.getTenantId();
                }
                ExecutionEntity processInstance = temp.getProcessInstance();
                if(processInstance!=null){
                    object = processInstance;
                }
                TaskEntity task = temp.getTask();
                if(task!=null){
                    object = task;
                }
            }
            if(object instanceof ExecutionEntityImpl){
                ExecutionEntityImpl temp = (ExecutionEntityImpl)object;
                hissActivitiEvent.setBusinessKey(temp.getBusinessKey());
                hissActivitiEvent.setVariables(temp.getVariables());
                return temp.getTenantId();
            }
            if(object instanceof TaskEntityImpl){
                TaskEntityImpl temp = (TaskEntityImpl)object;
                hissActivitiEvent.setBusinessKey(temp.getBusinessKey());
                hissActivitiEvent.setVariables(temp.getVariables());
                return temp.getTenantId();
            }
            log.error("========未识别的类型=========="+object.getClass().getName());
        }
        return null;
    }


    public void notifyClient(String tenantId,HissActivitiEvent hissActivitiEvent) {
        MessageContext messageContext = hissServerApperanceTemplate.eventActivitiProcessNotice(tenantId, hissActivitiEvent);
        log.trace("===========>:" + JSON.toJSONString(messageContext));
        failOnException = !messageContext.isSuccess();
    }

    @Override
    public boolean isFailOnException() {
        return failOnException;
    }

}
