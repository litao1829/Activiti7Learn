package cn.itcast.hiss.process.activiti.listener.impl;

import cn.itcast.hiss.process.activiti.util.RemoteExecuteClassUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.ActivitiListener;
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
 * @description JavaClass执行的动作，放到远程执行,多列执行
 * @date 2023/5/26 10:53
 * @version 1.0
 **/
@Component
@Scope("prototype")
@Slf4j
@Data
public class HissRemoteJavaClassEventListener implements ExecutionListener, TaskListener, CustomPropertiesResolver {

    protected ActivitiListener activitiListener;
    protected List<FieldDeclaration> fieldDeclarations;
    @Autowired
    protected HissServerApperanceTemplate hissServerApperanceTemplate;
    @Autowired
    protected RuntimeService runtimeService;

    // 自定义executerListener远程执行类，结果存储到流程全局变量上
    @Override
    public void notify(DelegateExecution execution) {
        RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,execution,activitiListener.getImplementation(),fieldDeclarations,true);
    }

    // 自定义taskListener远程执行类，结果存储到流程全局变量上
    @Override
    public void notify(DelegateTask delegateTask) {
        RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,delegateTask,activitiListener.getImplementation(),fieldDeclarations,false);
    }

    // 自定义属性远程执行类，不存储到流程变量上，直接返回
    @Override
    public Map<String, Object> getCustomPropertiesMap(DelegateExecution execution) {
        return RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,execution,activitiListener.getCustomPropertiesResolverImplementation(),fieldDeclarations,true);
    }
}
