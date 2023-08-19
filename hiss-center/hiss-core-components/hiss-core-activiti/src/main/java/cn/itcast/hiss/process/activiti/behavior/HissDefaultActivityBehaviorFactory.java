package cn.itcast.hiss.process.activiti.behavior;

import cn.itcast.hiss.process.activiti.behavior.servicetask.HissClassDeletgate;
import cn.itcast.hiss.process.activiti.behavior.servicetask.HissMailDeletgate;
import cn.itcast.hiss.process.activiti.behavior.servicetask.HissServiceTaskDelegateExpression;
import cn.itcast.hiss.process.activiti.properties.MailServerInfoProperties;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import org.activiti.bpmn.model.*;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.*;
import org.activiti.engine.impl.bpmn.helper.ClassDelegate;
import org.activiti.engine.impl.bpmn.parser.FieldDeclaration;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultActivityBehaviorFactory;
import org.activiti.engine.impl.delegate.ActivityBehavior;
import org.activiti.engine.impl.event.EventSubscriptionPayloadMappingProvider;
import org.activiti.runtime.api.impl.ExtensionsVariablesMappingProvider;
import org.activiti.runtime.api.impl.JsonMessagePayloadMappingProviderFactory;
import org.activiti.runtime.api.impl.MappingAwareCallActivityBehavior;
import org.activiti.spring.process.ProcessVariablesInitiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/*
 * @author miukoo
 * @description 重定义活动行文创建工厂
 * @date 2023/5/26 8:53
 * @version 1.0
 **/
@Component
public class HissDefaultActivityBehaviorFactory extends DefaultActivityBehaviorFactory {

    @Autowired
    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    MailServerInfoProperties mailServerInfoProperties;
    @Autowired
    ExtensionsVariablesMappingProvider variablesMappingProvider;
    @Autowired
    ProcessVariablesInitiator processVariablesInitiator;
    @Autowired
    EventSubscriptionPayloadMappingProvider eventSubscriptionPayloadMappingProvider;
    @Autowired
    VariablesPropagator variablesPropagator;

    @PostConstruct
    private void init(){
        this.setMessagePayloadMappingProviderFactory(new JsonMessagePayloadMappingProviderFactory(variablesMappingProvider));
    }

    /**
     * 重新定义用户行文，用户的表达式，通过远程去业务系统获取
     * @param userTask
     * @return
     */
    @Override
    public UserTaskActivityBehavior createUserTaskActivityBehavior(UserTask userTask) {
        return new HissUserTaskActivityBehavior(userTask,variablesMappingProvider,variablesPropagator);
    }



    /**
     * serviceTask
     * @param serviceTask
     * @return
     */
    public ClassDelegate createClassDelegateServiceTask(ServiceTask serviceTask) {
        HissClassDeletgate bean = new HissClassDeletgate(serviceTask.getId(),
                serviceTask.getImplementation(),
                createFieldDeclarations(serviceTask.getFieldExtensions()),
                getSkipExpressionFromServiceTask(serviceTask),
                serviceTask.getMapExceptions());
        bean.setRuntimeService(runtimeService);
        bean.setHissServerApperanceTemplate(hissServerApperanceTemplate);
        return bean;
    }

    /**
     * serviceTask DelegateExpression 执行的结果是一个类，发送到客户端执行
     * @param serviceTask
     * @return
     */
    public ServiceTaskDelegateExpressionActivityBehavior createServiceTaskDelegateExpressionActivityBehavior(ServiceTask serviceTask) {
        Expression delegateExpression = expressionManager.createExpression(serviceTask.getImplementation());
        return createServiceTaskBehavior(serviceTask,delegateExpression);
    }

    private ServiceTaskDelegateExpressionActivityBehavior createServiceTaskBehavior(ServiceTask serviceTask,Expression delegateExpression) {
        HissServiceTaskDelegateExpression bean = new HissServiceTaskDelegateExpression(serviceTask.getId(),
                delegateExpression,
                getSkipExpressionFromServiceTask(serviceTask),
                createFieldDeclarations(serviceTask.getFieldExtensions()));
        bean.setRuntimeService(runtimeService);
        bean.setHissServerApperanceTemplate(hissServerApperanceTemplate);
        return bean;
    }

    /**
     * serviceTask Expression 就在本地执行，并结果保存到变量中----------没变化
     * @param serviceTask
     * @return
     */
    public ServiceTaskExpressionActivityBehavior createServiceTaskExpressionActivityBehavior(ServiceTask serviceTask) {
        Expression expression = expressionManager.createExpression(serviceTask.getImplementation());
        return new ServiceTaskExpressionActivityBehavior(serviceTask.getId(),
                expression,
                getSkipExpressionFromServiceTask(serviceTask),
                serviceTask.getResultVariableName());
    }

    /**
     * WebService
     * @param serviceTask   --------- 和原来一样 ----------------- 推荐使用远程类
     * @return
     */
    @Deprecated
    public WebServiceActivityBehavior createWebServiceActivityBehavior(ServiceTask serviceTask) {
        return new WebServiceActivityBehavior();
    }

    /**
     * 获取内置变量 ${defaultServiceTaskBehavior}的值，并作为远程类名，进行执行
     * @param serviceTask
     * @return
     */
    public ActivityBehavior createDefaultServiceTaskBehavior(ServiceTask serviceTask) {
        Expression delegateExpression = expressionManager.createExpression("${" + DEFAULT_SERVICE_TASK_BEAN_NAME + "}");
        return createServiceTaskBehavior(serviceTask,delegateExpression);
    }

    /**
     * 创建发送邮件的行为
     * @param taskId
     * @param fields
     * @return
     */
    protected MailActivityBehavior createMailActivityBehavior(String taskId,List<FieldExtension> fields) {
        List<FieldDeclaration> fieldDeclarations = createFieldDeclarations(fields);
        HissMailDeletgate bean = new HissMailDeletgate();
        bean.setRuntimeService(runtimeService);
        bean.setHissServerApperanceTemplate(hissServerApperanceTemplate);
        bean.setMailServerInfoProperties(mailServerInfoProperties);
        ClassDelegate.applyFieldDeclaration(fieldDeclarations,bean);
        return bean;
    }

    public MailActivityBehavior createMailActivityBehavior(ServiceTask serviceTask) {
        return createMailActivityBehavior(serviceTask.getId(),serviceTask.getFieldExtensions());
    }

    public MailActivityBehavior createMailActivityBehavior(SendTask sendTask) {
        return createMailActivityBehavior(sendTask.getId(),sendTask.getFieldExtensions());
    }

    @Override
    protected CallActivityBehavior createCallActivityBehavior(Expression expression, List<MapExceptionEntry> mapExceptions) {
        return new MappingAwareCallActivityBehavior(expression, mapExceptions, variablesMappingProvider, processVariablesInitiator,
                variablesPropagator);
    }

    @Override
    protected CallActivityBehavior createCallActivityBehavior(String calledElement,
                                                              List<MapExceptionEntry> mapExceptions) {
        return new MappingAwareCallActivityBehavior(calledElement, mapExceptions, variablesMappingProvider, processVariablesInitiator,
                variablesPropagator);
    }


    // TODO mule 二期实现
    // TODO camel 二期实现
    // TODO shell 二期实现
}
