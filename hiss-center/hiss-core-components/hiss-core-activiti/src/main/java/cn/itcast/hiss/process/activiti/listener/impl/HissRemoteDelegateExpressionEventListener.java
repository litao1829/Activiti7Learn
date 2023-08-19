package cn.itcast.hiss.process.activiti.listener.impl;

import cn.itcast.hiss.process.activiti.util.RemoteExecuteClassUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.*;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
public class HissRemoteDelegateExpressionEventListener implements ExecutionListener, CustomPropertiesResolver,TaskListener {
    protected ActivitiListener activitiListener;
    protected Expression expression;
    protected List<FieldDeclaration> fieldDeclarations;
    @Autowired
    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    @Autowired
    protected RuntimeService runtimeService;

    public void notify(DelegateExecution execution) {
        Object delegate = expression.getValue(execution);
        if(delegate instanceof String){
            RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,execution,delegate.toString(),fieldDeclarations,true);
        } else {
            throw new ActivitiIllegalArgumentException("Delegate expression " + expression + " did not resolve to an implementation of " + ExecutionListener.class + " nor " + JavaDelegate.class);
        }
    }
    public String getExpressionText() {
        return expression.getExpressionText();
    }

    @Override
    public Map<String, Object> getCustomPropertiesMap(DelegateExecution execution) {
        Object delegate = expression.getValue(execution);
//        Object delegate = DelegateExpressionUtil.resolveDelegateExpression(expression, execution, fieldDeclarations);
        if(delegate instanceof String){
            return RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,execution,delegate.toString(),fieldDeclarations,false);
        } else {
            throw new ActivitiIllegalArgumentException("Delegate expression " + expression + " did not resolve to an implementation of " + ExecutionListener.class + " nor " + JavaDelegate.class);
        }
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        Object delegate = expression.getValue(delegateTask);
//        Object delegate = DelegateExpressionUtil.resolveDelegateExpression(expression, delegateTask, fieldDeclarations);
        if(delegate instanceof String){
            RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,delegateTask,delegate.toString(),fieldDeclarations,false);
        } else {
            throw new ActivitiIllegalArgumentException("Delegate expression " + expression + " did not resolve to an implementation of " + ExecutionListener.class + " nor " + JavaDelegate.class);
        }
    }
}
