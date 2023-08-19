package cn.itcast.hiss.process.activiti.configurator.validator;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;
import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.validation.ValidationError;
import org.activiti.validation.validator.Problems;
import org.activiti.validation.validator.ProcessLevelValidator;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/7/20 19:01
 * @version 1.0
 **/
public class HissSequenceflowValidator extends ProcessLevelValidator {

    @Override
    protected void executeValidation(BpmnModel bpmnModel, Process process, List<ValidationError> errors) {
        List<SequenceFlow> sequenceFlows = process.findFlowElementsOfType(SequenceFlow.class);
        for (SequenceFlow sequenceFlow : sequenceFlows) {

            String sourceRef = sequenceFlow.getSourceRef();
            String targetRef = sequenceFlow.getTargetRef();

            if (StringUtils.isEmpty(sourceRef)) {
                addError(errors, Problems.SEQ_FLOW_INVALID_SRC, process, sequenceFlow, "Invalid source for sequenceflow");
            }
            if (StringUtils.isEmpty(targetRef)) {
                addError(errors, Problems.SEQ_FLOW_INVALID_TARGET, process, sequenceFlow, "Invalid target for sequenceflow");
            }

            // Implicit check: sequence flow cannot cross (sub) process
            // boundaries, hence we check the parent and not the process
            // (could be subprocess for example)
            FlowElement source = process.getFlowElement(sourceRef, true);
            FlowElement target = process.getFlowElement(targetRef, true);

            // Src and target validation
            if (source == null) {
                addError(errors, Problems.SEQ_FLOW_INVALID_SRC, process, sequenceFlow, "Invalid source for sequenceflow");
            }
            if (target == null) {
                addError(errors, Problems.SEQ_FLOW_INVALID_TARGET, process, sequenceFlow, "Invalid target for sequenceflow");
            }

            if (source != null && target != null) {
                FlowElementsContainer sourceContainer = process.getFlowElementsContainer(source.getId());
                FlowElementsContainer targetContainer = process.getFlowElementsContainer(target.getId());

                if (sourceContainer == null) {
                    addError(errors, Problems.SEQ_FLOW_INVALID_SRC, process, sequenceFlow, "Invalid source for sequenceflow");
                }
                if (targetContainer == null) {
                    addError(errors, Problems.SEQ_FLOW_INVALID_TARGET, process, sequenceFlow, "Invalid target for sequenceflow");
                }
                if (sourceContainer != null && targetContainer != null && !sourceContainer.equals(targetContainer)) {
                    addError(errors, Problems.SEQ_FLOW_INVALID_TARGET, process, sequenceFlow, "Invalid target for sequenceflow, the target isn't defined in the same scope as the source");
                }
            }

            String conditionExpression = sequenceFlow.getConditionExpression();

            if (conditionExpression != null) {
                try {
                    if(conditionExpression.contains("execution")||conditionExpression.contains("hissVarLocal")||conditionExpression.contains("exchange")){
                        return;
                    }
                    new ExpressionFactoryImpl()
                            .createValueExpression(new SimpleContext(), conditionExpression.trim(), Object.class);
                } catch (Exception e) {
                    addError(errors, Problems.SEQ_FLOW_INVALID_CONDITIONAL_EXPRESSION, process, sequenceFlow, "Conditional expression is not valid");
                }
            }

        }
    }

}
