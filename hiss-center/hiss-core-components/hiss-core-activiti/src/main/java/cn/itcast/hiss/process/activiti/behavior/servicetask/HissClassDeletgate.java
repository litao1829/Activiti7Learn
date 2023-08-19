package cn.itcast.hiss.process.activiti.behavior.servicetask;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.process.activiti.util.RemoteExecuteClassUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.MapExceptionEntry;
import org.activiti.engine.DynamicBpmnConstants;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.helper.ErrorPropagation;
import org.activiti.engine.impl.bpmn.helper.SkipExpressionUtil;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
 * @author miukoo
 * @description 把service的java类发送到客户端去执行
 * @date 2023/5/29 17:53
 * @version 1.0
 **/
@Slf4j
public class HissClassDeletgate extends ClassDelegate {

    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    protected RuntimeService runtimeService;

    public HissClassDeletgate() {
        super("", null);
    }

    public HissClassDeletgate(String id, String className, List<FieldDeclaration> fieldDeclarations, Expression skipExpression, List<MapExceptionEntry> mapExceptions) {
        super(id, className, fieldDeclarations, skipExpression, mapExceptions);
    }

    public void execute(DelegateExecution execution) {
        boolean isSkipExpressionEnabled = SkipExpressionUtil.isSkipExpressionEnabled(execution, skipExpression);
        if (!isSkipExpressionEnabled || (isSkipExpressionEnabled && !SkipExpressionUtil.shouldSkipFlowElement(execution, skipExpression))) {
            if (Context.getProcessEngineConfiguration().isEnableProcessDefinitionInfoCache()) {
                ObjectNode taskElementProperties = Context.getBpmnOverrideElementProperties(serviceTaskId, execution.getProcessDefinitionId());
                if (taskElementProperties != null && taskElementProperties.has(DynamicBpmnConstants.SERVICE_TASK_CLASS_NAME)) {
                    String overrideClassName = taskElementProperties.get(DynamicBpmnConstants.SERVICE_TASK_CLASS_NAME).asText();
                    if (StringUtils.isNotEmpty(overrideClassName) && !overrideClassName.equals(className)) {
                        className = overrideClassName;
                    }
                }
            }
            try {
                // 判断是否是内部执行的任务，如果是，则内部执行
                if (className.startsWith(HissProcessConstants.INNER_CLASS_PREFIX)) {
                    String tempClassName = className;
                    try {
                        className = className.replace(HissProcessConstants.INNER_CLASS_PREFIX, "");
                        super.execute(execution);
                    } finally {
                        className = tempClassName;
                    }
                } else {
                    // 远程执行
                    RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate, runtimeService, execution, getClassName(), fieldDeclarations, true);
                }
            } catch (BpmnError error) {
                ErrorPropagation.propagateError(error, execution);
            } catch (RuntimeException e) {
                if (!ErrorPropagation.mapException(e, (ExecutionEntity) execution, mapExceptions))
                    throw e;
            }
        }
    }

    public void setHissServerApperanceTemplate(HissServerApperanceTemplate hissServerApperanceTemplate) {
        this.hissServerApperanceTemplate = hissServerApperanceTemplate;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }
}
