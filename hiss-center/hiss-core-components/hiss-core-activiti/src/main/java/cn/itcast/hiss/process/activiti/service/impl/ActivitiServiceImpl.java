package cn.itcast.hiss.process.activiti.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.service.ActivitiService;
import cn.itcast.hiss.process.activiti.service.UserTaskService;
import cn.itcast.hiss.process.activiti.util.VariableUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/*
 * @author miukoo
 * @description 流程办理各功能实现
 * @date 2023/6/3 11:19
 * @version 1.0
 **/
@Slf4j
@Service
@Transactional
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private HissServerApperanceTemplate hissServerApperanceTemplate;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public Set<String> doGetClientUserInfo(DelegateExecution execution, String assignee) {
        Set<String> resClientNames = new HashSet<>();
        Map<String, Object> variables = execution.getVariables();
        //判断是否是客户端变量--如果是客户端变量，那么就提取客户端请求信息
        if (VariableUtil.checkIsClientVariable(assignee)) {
            String[] varList = VariableUtil.getVariables(assignee);
            Object variableValue = variables.get(varList[0]);
            if (ObjectUtil.isNotNull(variableValue)) {
                //说明是流程变量
                if (variableValue.toString().startsWith(SystemConstant.CLIENT_FLAG)) {
                    //TODO 向客户端请求数据--需要获取流程发起人的信息
                    UserInfo userInfo = userTaskService.getBeforeNodeUserInfo(execution);
                    String[] split = variableValue.toString().split("/");
                    userInfo.setCurrentNodeVariable(split[1]);
                    userInfo.setCurrentNodeValue(varList[1]);
                    log.info("当前节点需要的用户信息:{},需要往客户端请求数据", assignee);
                    List<String> name = hissServerApperanceTemplate.getUserInfo(execution.getTenantId(), userInfo);
                    resClientNames.addAll(name);
                } else {
                    //说明是固定的值--不需要请求客户端
                    resClientNames.add(variableValue.toString());
                }
            } else {
                //虽然是变量 但客户端并未上报
                resClientNames.add(assignee);
            }
        } else {
            //不需要请求客户端直接添加
            resClientNames.add(assignee);
        }
        return resClientNames;
    }

    /**
     * 设计文件部署
     *
     * @param message
     * @param messageContext
     */
    @Override
    public void modelToDeploment(Message message, MessageContext messageContext) {
        ProcessDesignModel processDesignModel = (ProcessDesignModel) message.getPalyload();
        Model model = repositoryService.getModel(processDesignModel.getModelId());
        if (model != null) {
            Deployment deploy = modelToDeployment(model, message, messageContext);
            if (deploy == null) {// 发布有错误
                return;
            } else {
                messageContext.addResult("deployId", deploy.getId());
            }
        } else {
            messageContext.addError("msg", "未找到对应的model");
        }
    }

    /**
     * 通过model实现流程的启动
     *
     * @param message
     * @param messageContext
     * @param isInitForm 是否初始化表单数据
     */
    @Override
    public void startByModel(Message message, MessageContext messageContext,boolean isInitForm) {
        ProcessDesignModel processDesignModel = (ProcessDesignModel) message.getPalyload();
        Model model = repositoryService.getModel(processDesignModel.getModelId());
        if (model != null) {
            Deployment deploy = null;
            try{
                deploy = modelToDeployment(model, message, messageContext);
                if (deploy == null) {// 发布有错误
                    return;
                }
            }catch (ActivitiException e){
                e.printStackTrace();
                messageContext.addError("msg", "发布出错："+e.getMessage());
                return;
            }
            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deploy.getId())
                    .active()
                    .processDefinitionTenantId(model.getTenantId())
                    .processDefinitionResourceName(HissProcessConstants.PROCESS_RUN_XML).list();
            ProcessDefinition processDefinition = null;
            if(list.size()==0){
                processDefinition = list.get(0);
            }else{
                for (ProcessDefinition definition : list) {
                    BpmnModel bpmnModel = repositoryService.getBpmnModel(definition.getId());
                    if(bpmnModel.getMainProcess().isExecutable()){
                        processDefinition = definition;
                        break;
                    }
                }
            }
            if (processDefinition != null) {
                String title = model.getName();
                HashMap<String, Object> variables = new HashMap<>();
                // 发起人信息作为流程变量
                variables.put(SystemConstant.TASK_VARIABLES_CREATE_USERID, message.getMessageAuth().getCurrentUser().getUserId());
                variables.put(SystemConstant.TASK_VARIABLES_CREATE_USERNAME, message.getMessageAuth().getCurrentUser().getUserName());
                // 存储前端自定义变量
                if (processDesignModel.getVariables() != null) {
                    variables.putAll(processDesignModel.getVariables());
                }
                // 设置用户ID
                Authentication.setAuthenticatedUserId(message.getMessageAuth().getCurrentUser().getUserId());
                ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), processDesignModel.getBusinessKey(), variables);
                messageContext.addResultAndCount("processInstanceId", processInstance.getProcessInstanceId());
            } else {
                messageContext.addError("msg", "未找到对应的流程定义");
            }
        } else {
            messageContext.addError("msg", "未找到对应的model");
        }
    }


    /**
     * 模型发布
     *
     * @param model
     * @param message
     * @param messageContext
     */
    private Deployment modelToDeployment(Model model, Message message, MessageContext messageContext) {
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .key(model.getKey())
                .name(model.getName())
                .category(model.getCategory())
                .enableDuplicateFiltering()
                .tenantId(model.getTenantId());
        deploymentBuilder.addBytes(HissProcessConstants.PROCESS_RUN_XML, repositoryService.getModelEditorSource(model.getId()));
        Deployment deploy = deploymentBuilder.deploy();
        model.setDeploymentId(deploy.getId());
        repositoryService.saveModel(model);
        return deploy;
    }

}
