package cn.itcast.hiss.process.activiti.listener;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.process.activiti.listener.impl.*;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.CustomPropertiesResolver;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.delegate.TransactionDependentTaskListener;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.bpmn.helper.*;
import org.activiti.engine.impl.bpmn.parser.factory.DefaultListenerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 自定义监听器
 * @date 2023/5/26 9:01
 * @version 1.0
 **/
@Component
public class HissDefaultListenerFactory extends DefaultListenerFactory {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 通知抛出事件 -------------------   内部处理完成后，发送给远程客户端
     * @param eventListener
     * @return
     */
    @Override
    public ActivitiEventListener createEventThrowingEventListener(EventListener eventListener) {
        BaseDelegateEventListener result = null;
        if (ImplementationType.IMPLEMENTATION_TYPE_THROW_SIGNAL_EVENT.equals(eventListener.getImplementationType())) {
            result = new HissRemoteSignalThrowingEventListener();
            ((SignalThrowingEventListener) result).setSignalName(eventListener.getImplementation());
            ((SignalThrowingEventListener) result).setProcessInstanceScope(true);
        } else if (ImplementationType.IMPLEMENTATION_TYPE_THROW_GLOBAL_SIGNAL_EVENT.equals(eventListener.getImplementationType())) {
            result = new HissRemoteSignalThrowingEventListener();
            ((SignalThrowingEventListener) result).setSignalName(eventListener.getImplementation());
            ((SignalThrowingEventListener) result).setProcessInstanceScope(false);
        } else if (ImplementationType.IMPLEMENTATION_TYPE_THROW_MESSAGE_EVENT.equals(eventListener.getImplementationType())) {
            result = new HissRemoteMessageThrowingEventListener();
            ((MessageThrowingEventListener) result).setMessageName(eventListener.getImplementation());
        } else if (ImplementationType.IMPLEMENTATION_TYPE_THROW_ERROR_EVENT.equals(eventListener.getImplementationType())) {
            result = new HissRemoteErrorCodeThrowingEventListener();
            ((ErrorThrowingEventListener) result).setErrorCode(eventListener.getImplementation());
        }

        if (result == null) {
            throw new ActivitiIllegalArgumentException("Cannot create an event-throwing event-listener, unknown implementation type: " + eventListener.getImplementationType());
        }

        result.setEntityClass(getEntityType(eventListener.getEntityType()));
        return result;
    }

    /**
     * 创建 eventListener JavaClass监听器   ------- 发送到客户端去执行，通知客户端事件
     * @return
     */
    @Override
    public ActivitiEventListener createClassDelegateEventListener(EventListener eventListener) {
        HissRemoteJavaClassActivitiEventListener bean = applicationContext.getBean(HissRemoteJavaClassActivitiEventListener.class);
        bean.setClassName(eventListener.getImplementation());
        bean.setEntityClass(getEntityType(eventListener.getEntityType()));
        return bean;
    }

    /**
     * 创建 eventListener DelegateExpression监听器   ------- 发送到客户端去执行，通知客户端事件
     * @return
     */
    @Override
    public ActivitiEventListener createDelegateExpressionEventListener(EventListener eventListener) {
        HissRemoteDelegateExpressionActivitiEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionActivitiEventListener.class);
        bean.setExpression(expressionManager.createExpression(eventListener.getImplementation()));
        bean.setEntityClass(getEntityType(eventListener.getEntityType()));
        return bean;
    }

    /**
     * 创建 executionListener JavaClass监听器   ------- 发送到客户端去执行，执行的结果存储到变量中
     * @return
     */
    @Override
    public TaskListener createClassDelegateTaskListener(ActivitiListener activitiListener) {
        // 判断是否是内部执行的任务，如果是，则内部执行
        String implementationType = activitiListener.getImplementationType();
        if(ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(implementationType)){
            String implementation = activitiListener.getImplementation();
            if(implementation.startsWith(HissProcessConstants.INNER_CLASS_PREFIX)){
                String newName = implementation.replace(HissProcessConstants.INNER_CLASS_PREFIX, "");
                activitiListener.setImplementation(newName);
                return super.createClassDelegateTaskListener(activitiListener);
            }
        }
        HissRemoteJavaClassEventListener bean = applicationContext.getBean(HissRemoteJavaClassEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }

    /**
     * 创建 executionListener Expression监听器  ------- 执行后的值，存储到变量中
     * @param activitiListener
     * @return
     */
    @Override
    public TaskListener createExpressionTaskListener(ActivitiListener activitiListener) {
        HissExpressionExecutionListener bean = applicationContext.getBean(HissExpressionExecutionListener.class);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        return bean;
    }

    /**
     * 创建 executionListener Delegate Expression监听器  ------- 代理表达式执行的结果，作为客户端类去远程执行
     * @param activitiListener
     * @return
     */
    @Override
    public TaskListener createDelegateExpressionTaskListener(ActivitiListener activitiListener) {
        HissRemoteDelegateExpressionEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }
    /**
     * 创建 executionListener Delegate Expression监听器  ------- 代理表达式执行的结果，作为客户端类去远程执行
     * @param activitiListener
     * @return
     */
    @Override
    public TransactionDependentTaskListener createTransactionDependentDelegateExpressionTaskListener(ActivitiListener activitiListener) {
        HissRemoteDelegateExpressionTransactionDependentEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionTransactionDependentEventListener.class);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }

    /**
     * 创建 executionListener JavaClass监听器   ------- 发送到客户端去执行，执行的结果存储到变量中
     * @return
     */
    public ExecutionListener createClassDelegateExecutionListener(ActivitiListener activitiListener) {
        // 判断是否是内部执行的任务，如果是，则内部执行
        String implementationType = activitiListener.getImplementationType();
        if(ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(implementationType)){
            String implementation = activitiListener.getImplementation();
            if(implementation.startsWith(HissProcessConstants.INNER_CLASS_PREFIX)){
                String newName = implementation.replace(HissProcessConstants.INNER_CLASS_PREFIX, "");
                activitiListener.setImplementation(newName);
                return super.createClassDelegateExecutionListener(activitiListener);
            }
        }
        HissRemoteJavaClassEventListener bean = applicationContext.getBean(HissRemoteJavaClassEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }

    /**
     * 创建 executionListener Expression监听器  ------- 执行后的值，存储到变量中
     * @param activitiListener
     * @return
     */
    @Override
    public ExecutionListener createExpressionExecutionListener(ActivitiListener activitiListener) {
        HissExpressionExecutionListener bean = applicationContext.getBean(HissExpressionExecutionListener.class);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        return bean;
    }

    /**
     * 创建 executionListener Delegate Expression监听器  ------- 代理表达式执行的结果，作为客户端类去远程执行
     * @param activitiListener
     * @return
     */
    @Override
    public ExecutionListener createDelegateExpressionExecutionListener(ActivitiListener activitiListener) {
        HissRemoteDelegateExpressionEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }

    /**
     * 创建 executionListener Delegate Expression监听器  ------- 代理表达式执行的结果，作为客户端类去远程执行
     * @param activitiListener
     * @return
     */
    @Override
    public HissRemoteDelegateExpressionTransactionDependentEventListener createTransactionDependentDelegateExpressionExecutionListener(ActivitiListener activitiListener) {
        HissRemoteDelegateExpressionTransactionDependentEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionTransactionDependentEventListener.class);
        bean.setExpression(expressionManager.createExpression(activitiListener.getImplementation()));
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }

    /**
     * 自定义属性加载器   ------- 对应的Class发送到客户端执行,客户端返回的数据不存储带流程变量中，直接作为属性返回
     * @param activitiListener
     * @return
     */
    public CustomPropertiesResolver createClassDelegateCustomPropertiesResolver(ActivitiListener activitiListener) {
        // 判断是否是内部执行的任务，如果是，则内部执行
        String implementationType = activitiListener.getImplementationType();
        if(ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(implementationType)){
            String implementation = activitiListener.getImplementation();
            if(implementation.startsWith(HissProcessConstants.INNER_CLASS_PREFIX)){
                String newName = implementation.replace(HissProcessConstants.INNER_CLASS_PREFIX, "");
                activitiListener.setImplementation(newName);
                return super.createClassDelegateCustomPropertiesResolver(activitiListener);
            }
        }
        HissRemoteJavaClassEventListener bean = applicationContext.getBean(HissRemoteJavaClassEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setFieldDeclarations(null);
        return bean;
    }

    /**
     * 创建 executionListener Expression监听器  ------- 执行后的值，返回自定义属性
     * @param activitiListener
     * @return
     */
    @Override
    public CustomPropertiesResolver createExpressionCustomPropertiesResolver(ActivitiListener activitiListener) {
        HissExpressionExecutionListener bean = applicationContext.getBean(HissExpressionExecutionListener.class);
        bean.setExpression(expressionManager.createExpression(activitiListener.getCustomPropertiesResolverImplementation()));
        return bean;
    }

    /**
     * 创建 executionListener Delegate Expression监听器  ------- 代理表达式执行的结果，作为客户端类去远程执行
     * @param activitiListener
     * @return
     */
    @Override
    public CustomPropertiesResolver createDelegateExpressionCustomPropertiesResolver(ActivitiListener activitiListener) {
        HissRemoteDelegateExpressionEventListener bean = applicationContext.getBean(HissRemoteDelegateExpressionEventListener.class);
        bean.setActivitiListener(activitiListener);
        bean.setExpression(expressionManager.createExpression(activitiListener.getCustomPropertiesResolverImplementation()));
        bean.setFieldDeclarations(createFieldDeclarations(activitiListener.getFieldExtensions()));
        return bean;
    }


}
