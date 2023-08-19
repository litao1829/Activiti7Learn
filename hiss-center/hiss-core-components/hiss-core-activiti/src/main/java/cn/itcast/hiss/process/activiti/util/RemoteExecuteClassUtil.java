package cn.itcast.hiss.process.activiti.util;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.listener.HissActivitiEventBuilder;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.VariableScope;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author miukoo
 * @description 远程客户端执行类处理
 * @date 2023/5/29 17:59
 * @version 1.0
 **/
@Slf4j
public class RemoteExecuteClassUtil {

    private static Map parseMessageContext(MessageContext messageContext, VariableScope variableScope, boolean isSave){
        if(!messageContext.isSuccess()){
            ConcurrentHashMap<String, Object> error = messageContext.getError();
            if(error.containsKey(HissProcessConstants.BPMN_ERROR)){
                throw new BpmnError((String) error.get(HissProcessConstants.BPMN_ERROR));
            }
            throw new RuntimeException(""+messageContext.getError().get(EventOperationTypeEnum.EXECUTE_CLASS.name()));
        }else{
            Object result = messageContext.getResult().get(EventOperationTypeEnum.EXECUTE_CLASS.name());
            if(result instanceof Map){
                Map map = (Map) result;
                if(isSave) {
                    variableScope.setVariables(map);
                }
                return map;
            }
            if(result instanceof JSONObject){
                JSONObject jsonObject =(JSONObject)result;
                HashMap map = jsonObject.toJavaObject(HashMap.class);
                if(isSave) {
                    variableScope.setVariables(map);
                }
                return map;
            }
        }
        return null;
    }

    public static Map notifyClient(HissServerApperanceTemplate hissServerApperanceTemplate,
                                   RuntimeService runtimeService,
                                   String tenantId, HissActivitiEvent hissActivitiEvent, VariableScope variableScope, boolean isSave) {
        // 统一填充businessKey
        if(StrUtil.isEmpty(hissActivitiEvent.getBusinessKey())&&StrUtil.isNotEmpty(hissActivitiEvent.getProcessInstanceId())){
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(hissActivitiEvent.getProcessInstanceId()).singleResult();
            if(processInstance!=null){
                hissActivitiEvent.setBusinessKey(processInstance.getBusinessKey());
                hissActivitiEvent.setVariables(processInstance.getProcessVariables());
            }
        }
        MessageContext messageContext = hissServerApperanceTemplate.eventActivitiProcessNotice(tenantId, hissActivitiEvent);
        log.trace("===========>:" + JSON.toJSONString(messageContext));
        return parseMessageContext(messageContext,variableScope,isSave);
    }

    /**
     * @param hissServerApperanceTemplate
     * @param runtimeService
     * @param execution
     * @param className
     * @param fieldDeclarations
     * @param isSave
     * @return
     */
    public static Map notifyClient(HissServerApperanceTemplate hissServerApperanceTemplate,
                                   RuntimeService runtimeService,
                                   DelegateExecution execution,
                                   String className,
                                   List<FieldDeclaration> fieldDeclarations,
                                   boolean isSave) {
        HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder()
                .execution(execution)
                .targetName(className)
                .fileds(fieldDeclarations)
                .build();
        return notifyClient(hissServerApperanceTemplate,runtimeService,execution.getTenantId(),hissActivitiEvent,execution,true);
    }


    public static Map notifyClient(HissServerApperanceTemplate hissServerApperanceTemplate, RuntimeService runtimeService,
                                    DelegateTask delegateTask, String className, List<FieldDeclaration> fieldDeclarations, boolean b) {
        HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder()
                .delegateTask(delegateTask)
                .targetName(className)
                .fileds(fieldDeclarations)
                .build();
        return notifyClient(hissServerApperanceTemplate,runtimeService,delegateTask.getTenantId(),hissActivitiEvent,delegateTask,true);
    }
}
