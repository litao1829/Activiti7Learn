package cn.itcast.hiss.process.activiti.listener;

import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.Field;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageBuilder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * @author miukoo
 * @description 推送到客户端执行的消息构建器
 * @date 2023/5/27 16:12
 * @version 1.0
 **/
public class HissActivitiEventBuilder {

    private HissActivitiEvent hissActivitiEvent = new HissActivitiEvent();

    public static HissActivitiEventBuilder builder(){
        return new HissActivitiEventBuilder();
    }

    public HissActivitiEventBuilder execution(DelegateExecution execution){
        hissActivitiEvent.setEventName(execution.getEventName());
        hissActivitiEvent.setProcessDefinitionId(execution.getProcessDefinitionId());
        hissActivitiEvent.setProcessInstanceId(execution.getProcessInstanceId());
        hissActivitiEvent.setOperationType(EventOperationTypeEnum.EXECUTE_CLASS);
        hissActivitiEvent.setVariables(execution.getVariables());
        hissActivitiEvent.setVariablesLocal(execution.getVariablesLocal());
        hissActivitiEvent.setBusinessKey(execution.getProcessInstanceBusinessKey());
        return this;
    }

    public HissActivitiEventBuilder delegateTask(DelegateTask delegateTask){
        hissActivitiEvent.setEventName(delegateTask.getEventName());
        hissActivitiEvent.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
        hissActivitiEvent.setProcessInstanceId(delegateTask.getProcessInstanceId());
        hissActivitiEvent.setExecutionId(delegateTask.getExecutionId());
        hissActivitiEvent.setOperationType(EventOperationTypeEnum.EXECUTE_CLASS);
        hissActivitiEvent.setVariables(delegateTask.getVariables());
        hissActivitiEvent.setVariablesLocal(delegateTask.getVariablesLocal());
        return this;
    }

    public HissActivitiEventBuilder targetName(String className){
        hissActivitiEvent.setTargetName(className);
        return this;
    }

    public HissActivitiEventBuilder fileds(List<FieldDeclaration> fields) {
        if(fields!=null){
            List<Field> fieldList = new ArrayList<>();
            for (FieldDeclaration field : fields) {
                Field f = new Field();
                BeanUtils.copyProperties(field,f);
                fieldList.add(f);
            }
            hissActivitiEvent.setFields(fieldList);
        }
        return this;
    }

    public HissActivitiEvent build(){
        return hissActivitiEvent;
    }


    public HissActivitiEventBuilder object(Object object) {
        if(object instanceof DelegateTask){
            DelegateTask delegateTask = (DelegateTask)object;
            return delegateTask(delegateTask);
        }
        if(object instanceof DelegateExecution){
            DelegateExecution execution = (DelegateExecution)object;
            return execution(execution);
        }
        return this;
    }

    public HissActivitiEventBuilder eventName(String name) {
        hissActivitiEvent.setEventName(name);
        return this;
    }

    public HissActivitiEventBuilder activitiEvent(ActivitiEvent event) {
        hissActivitiEvent.setProcessDefinitionId(event.getProcessDefinitionId());
        hissActivitiEvent.setExecutionId(event.getExecutionId());
        hissActivitiEvent.setProcessInstanceId(event.getProcessInstanceId());
        hissActivitiEvent.setEventData(event);
        return this;
    }
}
