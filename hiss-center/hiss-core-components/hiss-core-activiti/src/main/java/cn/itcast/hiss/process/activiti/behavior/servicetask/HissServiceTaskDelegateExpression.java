package cn.itcast.hiss.process.activiti.behavior.servicetask;

import cn.itcast.hiss.process.activiti.util.RemoteExecuteClassUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.DynamicBpmnConstants;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior;
import org.activiti.engine.impl.bpmn.helper.ErrorPropagation;
import org.activiti.engine.impl.bpmn.helper.SkipExpressionUtil;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.context.Context;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
 * @author miukoo
 * @description 代理表达式
 * @date 2023/5/29 19:24
 * @version 1.0
 **/
@Slf4j
public class HissServiceTaskDelegateExpression extends ServiceTaskDelegateExpressionActivityBehavior {
    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    protected RuntimeService runtimeService;
    protected List<FieldDeclaration> fieldDeclarations;

    public HissServiceTaskDelegateExpression() {
        super("", null, null, null);
    }

    public HissServiceTaskDelegateExpression(String serviceTaskId, Expression expression, Expression skipExpression, List<FieldDeclaration> fieldDeclarations) {
        super(serviceTaskId, expression, skipExpression, fieldDeclarations);
        this.fieldDeclarations = fieldDeclarations;
    }

    public void setHissServerApperanceTemplate(HissServerApperanceTemplate hissServerApperanceTemplate) {
        this.hissServerApperanceTemplate = hissServerApperanceTemplate;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void execute(DelegateExecution execution) {

        try {
            boolean isSkipExpressionEnabled = SkipExpressionUtil.isSkipExpressionEnabled(execution, skipExpression);
            if (!isSkipExpressionEnabled || (isSkipExpressionEnabled && !SkipExpressionUtil.shouldSkipFlowElement(execution, skipExpression))) {

                if (Context.getProcessEngineConfiguration().isEnableProcessDefinitionInfoCache()) {
                    ObjectNode taskElementProperties = Context.getBpmnOverrideElementProperties(serviceTaskId, execution.getProcessDefinitionId());
                    if (taskElementProperties != null && taskElementProperties.has(DynamicBpmnConstants.SERVICE_TASK_DELEGATE_EXPRESSION)) {
                        String overrideExpression = taskElementProperties.get(DynamicBpmnConstants.SERVICE_TASK_DELEGATE_EXPRESSION).asText();
                        if (StringUtils.isNotEmpty(overrideExpression) && !overrideExpression.equals(expression.getExpressionText())) {
                            expression = Context.getProcessEngineConfiguration().getExpressionManager().createExpression(overrideExpression);
                        }
                    }
                }
                Object delegate = expression.getValue(execution);
                if (delegate instanceof String) {
                    RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate, runtimeService, execution, delegate.toString(), fieldDeclarations, true);
                    leave(execution);
                } else {
                    throw new ActivitiIllegalArgumentException("Delegate expression " + expression + " is not a string");
                }
            } else {
                leave(execution);
            }
        } catch (Exception exc) {

            Throwable cause = exc;
            BpmnError error = null;
            while (cause != null) {
                if (cause instanceof BpmnError) {
                    error = (BpmnError) cause;
                    break;
                }
                cause = cause.getCause();
            }

            if (error != null) {
                ErrorPropagation.propagateError(error, execution);
            } else {
                throw new ActivitiException(exc.getMessage(), exc);
            }

        }
    }

}
