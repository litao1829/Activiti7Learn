package cn.itcast.hiss.process.activiti.listener.impl;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.listener.HissActivitiEventBuilder;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Task;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.bpmn.listener.DelegateExpressionTransactionDependentExecutionListener;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.el.NoExecutionVariableScope;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/27 19:51
 * @version 1.0
 **/
@Component
@Scope("prototype")
@Slf4j
public class HissRemoteDelegateExpressionTransactionDependentEventListener extends DelegateExpressionTransactionDependentExecutionListener implements TransactionDependentExecutionListener,TransactionDependentTaskListener {

    protected List<FieldDeclaration> fieldDeclarations;
    @Autowired
    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    @Autowired
    RuntimeService runtimeService;

    public HissRemoteDelegateExpressionTransactionDependentEventListener(){
        super(null);
    }

    @Override
    public void notify(String processInstanceId, String executionId, FlowElement flowElement, Map<String, Object> executionVariables, Map<String, Object> customPropertiesMap) {
        notifyRemote(processInstanceId,executionId,executionVariables,customPropertiesMap);
    }

    private Map parseMessageContext(MessageContext messageContext, VariableScope variableScope){
        if(!messageContext.isSuccess()){
            throw new RuntimeException(""+messageContext.getError().get(EventOperationTypeEnum.EXECUTE_CLASS.name()));
        }else{
            Object result = messageContext.getResult().get(EventOperationTypeEnum.EXECUTE_CLASS.name());
            if(result instanceof Map){
                Map map = (Map) result;
                variableScope.setVariables(map);
                return map;
            }
            if(result instanceof JSONObject){
                JSONObject jsonObject =(JSONObject)result;
                HashMap map = jsonObject.toJavaObject(HashMap.class);
                variableScope.setVariables(map);
                return map;
            }
        }
        return null;
    }

    public Map notifyClient(String tenantId,HissActivitiEvent hissActivitiEvent,VariableScope variableScope) {
        MessageContext messageContext = hissServerApperanceTemplate.eventActivitiProcessNotice(tenantId, hissActivitiEvent);
        log.trace("===========>:" + JSON.toJSONString(messageContext));
        return parseMessageContext(messageContext,variableScope);
    }

    public String getExpressionText() {
        return expression.getExpressionText();
    }

    public void setExpression(Expression expression){
        this.expression = expression;
    }
    @Override
    public void notify(String processInstanceId, String executionId, Task task, Map<String, Object> executionVariables, Map<String, Object> customPropertiesMap) {
        notifyRemote(processInstanceId,executionId,executionVariables,customPropertiesMap);
    }

    public void notifyRemote(String processInstanceId, String executionId, Map<String, Object> executionVariables, Map<String, Object> customPropertiesMap) {
        NoExecutionVariableScope scope = new NoExecutionVariableScope();
        Object delegate = expression.getValue(scope);
        if(delegate instanceof String){
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder()
                    .targetName(delegate.toString())
                    .fileds(fieldDeclarations)
                    .build();
            hissActivitiEvent.setProcessInstanceId(processInstanceId);
            hissActivitiEvent.setExecutionId(executionId);
            hissActivitiEvent.setProcessDefinitionId(processInstance.getProcessDefinitionId());
            hissActivitiEvent.setCustomPropertiesMap(customPropertiesMap);
            if(processInstance!=null){
                hissActivitiEvent.setBusinessKey(processInstance.getBusinessKey());
                hissActivitiEvent.setVariables(processInstance.getProcessVariables());
            }
            if(StrUtil.isNotEmpty(executionId)){
                hissActivitiEvent.setVariables(runtimeService.getVariables(executionId));
                hissActivitiEvent.setVariablesLocal(runtimeService.getVariablesLocal(executionId));
            }else{
                hissActivitiEvent.setVariables(processInstance.getProcessVariables());
            }
            // 把执行结果写会到流程全局变量中
            Map map = notifyClient(processInstance.getTenantId(), hissActivitiEvent, scope);
            if(map!=null&&StrUtil.isNotEmpty(executionId)){
                runtimeService.setVariables(executionId,map);
            }
        } else {
            throw new ActivitiIllegalArgumentException("Delegate expression " + expression + " did not is string");
        }
    }

    public void setFieldDeclarations(List<FieldDeclaration> fieldDeclarations) {
        this.fieldDeclarations = fieldDeclarations;
    }
}
