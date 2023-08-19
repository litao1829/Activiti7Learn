package cn.itcast.hiss.process.activiti.listener.impl;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.listener.HissActivitiEventBuilder;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.*;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.bpmn.helper.BaseDelegateEventListener;
import org.activiti.engine.impl.bpmn.helper.DelegateExpressionUtil;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 代理表达式本地执行，获取到类名称，发送到远程执行
 * @date 2023/5/27 16:06
 * @version 1.0
 **/
@Component
@Scope("prototype")
@Slf4j
@Data
public class HissRemoteDelegateExpressionActivitiEventListener extends BaseDelegateEventListener {

    protected ActivitiListener activitiListener;
    protected Expression expression;
    protected boolean failOnException = false;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    HissServerApperanceTemplate hissServerApperanceTemplate;

    @Override
    public void onEvent(ActivitiEvent event) {
        if (isValidEvent(event)) {
            Object delegate = DelegateExpressionUtil.resolveDelegateExpression(expression, new NoExecutionVariableScope());
            if(delegate instanceof String) {
                HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder()
                        .targetName(delegate.toString())
                        .eventName(event.getType().name())
                        .activitiEvent(event)
                        .build();
                hissActivitiEvent.setOperationType(EventOperationTypeEnum.EXECUTE_CLASS);
                ProcessDefinition processDefinition = repositoryService.getProcessDefinition(event.getProcessDefinitionId());
                if(StrUtil.isNotEmpty(event.getProcessInstanceId())){
                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(event.getProcessInstanceId()).singleResult();
                    if(processInstance!=null){
                        hissActivitiEvent.setBusinessKey(processInstance.getBusinessKey());
                        hissActivitiEvent.setVariables(processInstance.getProcessVariables());
                    }
                }
                notifyClient(processDefinition.getTenantId(), hissActivitiEvent);
            }
        }
    }

    public void notifyClient(String tenantId,HissActivitiEvent hissActivitiEvent) {
        MessageContext messageContext = hissServerApperanceTemplate.eventActivitiProcessNotice(tenantId, hissActivitiEvent);
        log.trace("===========>:" + JSON.toJSONString(messageContext));
        // TODO 事件通知，可以失败重试、另外的消息通道通知等，保障通知的可靠性
        this.failOnException = !messageContext.isSuccess();
    }

    @Override
    public boolean isFailOnException() {
        return failOnException;
    }
}
