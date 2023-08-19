package cn.itcast.hiss.process.activiti.listener.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
 * @author miukoo
 * @description 表达式
 * @date 2023/5/27 16:57
 * @version 1.0
 **/
@Component
@Scope("prototype")
@Slf4j
@Data
public class HissExpressionExecutionListener implements ExecutionListener, CustomPropertiesResolver, TaskListener {

    protected Expression expression;
    @Autowired
    RepositoryService repositoryService;

    public void notify(DelegateExecution execution) {
        Object value = expression.getValue(execution);
        if(value!=null){
            execution.setVariable("expressionResult",value);
        }
    }

    public String getExpressionText() {
        return expression.getExpressionText();
    }

    @Override
    public Map<String, Object> getCustomPropertiesMap(DelegateExecution execution) {
        Object expressionValue = expression.getValue(execution);
        if (expressionValue instanceof Map) {
            return (Map<String, Object>) expressionValue;
        } else {
            throw new ActivitiIllegalArgumentException("Custom properties resolver expression " + expression + " did not return a Map<String, Object>");
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Object value = expression.getValue(delegateTask);
        if(value!=null){
            delegateTask.setVariable("expressionResult",value);
        }
    }
}
