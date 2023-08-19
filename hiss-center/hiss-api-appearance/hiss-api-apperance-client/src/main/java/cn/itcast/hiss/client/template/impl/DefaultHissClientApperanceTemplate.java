package cn.itcast.hiss.client.template.impl;

import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.api.client.common.PageInfo;
import cn.itcast.hiss.api.client.form.CreateFormDefinition;
import cn.itcast.hiss.api.client.form.FormSubmitData;
import cn.itcast.hiss.api.client.form.GetFormDefinition;
import cn.itcast.hiss.api.client.processdefinition.DeleteProcessDefinition;
import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.api.client.processinstance.CreateProcessInstance;
import cn.itcast.hiss.api.client.processinstance.HissProcessInstance;
import cn.itcast.hiss.api.client.task.*;
import cn.itcast.hiss.client.template.HissClientApperanceTemplate;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.*;
import cn.itcast.hiss.message.sender.MessageSenderExecuter;
import cn.itcast.hiss.message.sender.common.PageInfoMessage;
import cn.itcast.hiss.message.sender.common.VariableMessage;
import cn.itcast.hiss.message.sender.form.CreateFormDefinitionMessage;
import cn.itcast.hiss.message.sender.form.FormSubmitDataMessage;
import cn.itcast.hiss.message.sender.form.GetFormDefinitionByIdMessage;
import cn.itcast.hiss.message.sender.processdefinition.DeleteProcessDefinitionMessage;
import cn.itcast.hiss.message.sender.processdefinition.ProcessDesignModelMessage;
import cn.itcast.hiss.message.sender.processinstance.CreateProcessInstanceMessage;
import cn.itcast.hiss.message.sender.processinstance.HissProcessInstanceMessage;
import cn.itcast.hiss.message.sender.task.*;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.ProcessInstanceMessage;
import cn.itcast.hiss.message.sys.ProcessModelMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessInstance;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/*
 * @author miukoo
 * @description 客户端使用模板类
 * 1、flowM 流程定义相关
 * 2、flowB 流程办理相关
 * @date 2023/5/24 17:08
 * @version 1.0
 **/
public class DefaultHissClientApperanceTemplate implements HissClientApperanceTemplate {

    @Autowired
    MessageSenderExecuter messageSenderExecuter;

    @Override
    public MessageContext listProcessCategory(String tennetId, ProcessCategory processCategory, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESSS_CATEGORY_LIST.getId())
                .palyload(processCategory).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessCategoryMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listFormCategory(String tennetId, ProcessCategory processCategory, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_FORM_CATEGORY_LIST.getId())
                .palyload(processCategory).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessCategoryMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listCategoryProcess(String tennetId, ProcessModel processModel, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_MODEL_LIST.getId())
                .palyload(processModel).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessModelMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listApplayProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_APPLY_LIST.getId())
                .palyload(processInstance).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext deleteApplayProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_APPLY_DELETE.getId())
                .palyload(processInstance).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext deleteProcess(String tennetId, ProcessModel processModel, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_MODEL_DELETE.getId())
                .palyload(processModel).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessModelMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listHandleProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_HANDLE_LIST.getId())
                .palyload(processInstance).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext deleteFormModel(String tennetId, ProcessModel processModel, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_FORM_MODEL_DELETE.getId())
                .palyload(processModel).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessModelMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listFormModel(String tennetId, ProcessModel processModel, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_FORM_MODEL_LIST.getId())
                .palyload(processModel).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessModelMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext listProcessInstance(String tennetId, ProcessInstance processInstance, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_INSTANCE_LIST.getId())
                .palyload(processInstance).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    @Override
    public MessageContext deleteProcessInstance(String tennetId, ProcessInstance processInstance, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.CLI_PROCESS_INSTANCE_DELETE.getId())
                .palyload(processInstance).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 启动业务流程
     * @param tennetId
     * @param processDesignModel
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext startBisPreProcess(String tennetId, ProcessDesignModel processDesignModel, CurrentUser currentUser) {
        Message message = MessageBuilder.builder()
                .id(HandlerIdClientEnum.CLI_FLOW_M_PRE_START_MODEL_FOR_BIS.getId())
                .palyload(processDesignModel).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ProcessDesignModelMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取流程定义（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public MessageContext getProcessDefination(String tennetId, PageInfo payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.PD_GET_DEPLOYMENT.getId()).palyload(payload).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取流程定义（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public void getProcessDefination(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        message.setId(HandlerIdClientEnum.PD_GET_DEPLOYMENT.getId());
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 删除流程定义（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public MessageContext deleteProcessDefinitionMessage(String tennetId, DeleteProcessDefinition payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(DeleteProcessDefinitionMessage.class);
        message.setId(HandlerIdClientEnum.PD_DELETE_DEFINITION.getId());
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 删除流程定义（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    @Override
    public void deleteProcessDefinitionMessage(String tennetId, DeleteProcessDefinition payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(DeleteProcessDefinitionMessage.class);
        message.setId(HandlerIdClientEnum.PD_DELETE_DEFINITION.getId());
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 创建流程实例（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public MessageContext createProcessInstance(String tennetId, CreateProcessInstance payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_CREATE_INSTANCE.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(CreateProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 创建流程实例（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    @Override
    public void createProcessInstance(String tennetId, CreateProcessInstance payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_CREATE_INSTANCE.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(CreateProcessInstanceMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 获取流程实例（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public MessageContext getProcessInstance(String tennetId, PageInfo payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_GET_INSTANCE.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取流程实例（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    @Override
    public void getProcessInstance(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_GET_INSTANCE.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 开启流程实例（同步）
     *
     * @param tennetId
     * @param payload
     * @return
     */
    @Override
    public MessageContext startProcessInstance(String tennetId, HissProcessInstance payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_START_PROCESS.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(HissProcessInstanceMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 开启流程实例（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    @Override
    public void startProcessInstance(String tennetId, HissProcessInstance payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.PI_START_PROCESS.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(HissProcessInstanceMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    //========================================TASK=============================================

    /**
     * 获取我的任务（同步）
     *
     * @param tennetId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext getMyTask(String tennetId, PageInfo payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_GET_MY_TASK.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取我的任务（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void getMyTask(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_GET_MY_TASK.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(PageInfoMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 完成任务（同步）
     *
     * @param tennetId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext approveTask(String tennetId, ApproveTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_APPROVE_TASK.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ApproveTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 完成任务（异步）
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void approveTask(String tennetId, ApproveTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_APPROVE_TASK.getId()).tenant(tennetId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(ApproveTaskMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    @Override
    public MessageContext sendVariable(String tenantId, List<HissVariable> hissVariableList) {
        Message message = MessageBuilder.builder().palyload(hissVariableList).id(HandlerIdClientEnum.SYS_VARIABLE.getId()).tenant(tenantId)
                .build(VariableMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 获取客户端变量（同步）
     * @param tenantId
     * @return
     */
    @Override
    public MessageContext getClientVariable(String tenantId) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.SYS_GET_VARIABLE.getId()).tenant(tenantId)
                .build();
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 不同意任务（异步）
     *
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void rejectTask(String tenantId, RejectTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_REJECT_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(RejectTaskMessage.class);
        messageSenderExecuter.sendMessage(message, messageCallback);
    }

    /**
     * 不同意任务（同步）
     *
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext rejectTask(String tenantId, RejectTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_REJECT_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(RejectTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 任务回退（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext rollbackTask(String tenantId, AnyJumpTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_ROLLBACK_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AnyJumpTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 任务回退（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void rollbackTask(String tenantId, AnyJumpTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_ROLLBACK_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AnyJumpTaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }

    /**
     * 委派任务（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext delegateTask(String tenantId,DelegateTask payload,CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_DELEGATE_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(DelegateTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 委派任务（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void delegateTask(String tenantId, DelegateTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_DELEGATE_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(DelegateTaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }


    /**
     * 后加签（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void afterAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_ADDEXECUTION_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }

    /**
     * 后加签（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext afterAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_ADDEXECUTION_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 前加签（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void beforeAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_BEFORE_SIGN_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        messageSenderExecuter.sendMessage(message);
    }

    /**
     * 前加签（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext beforeAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_BEFORE_SIGN_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 拾取任务（同步）
      * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext claimTask(String tenantId, HissTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_CLAIM_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(TaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 拾取任务（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void claimTask(String tenantId, HissTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_CLAIM_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(TaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }

    /**
     * 并加签（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void parallelAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_PARALLEL_SIGN_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }


    /**
     * 并加签（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext parallelAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_PARALLEL_SIGN_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(AddExecutionTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 发送信号消息（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void tiggerMessage(String tenantId, TiggerTask payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_TIGGER_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(TiggerTaskMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }

    /**
     * 发送信号消息（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext tiggerMessage(String tenantId, TiggerTask payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.TASK_TIGGER_TASK.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(TiggerTaskMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 创建表单（同步）
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext createForm(String tenantId, CreateFormDefinition payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.FORM_CREATE_FORM_DIFINITION.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(CreateFormDefinitionMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 创建表单（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void createForm(String tenantId, CreateFormDefinition payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.FORM_CREATE_FORM_DIFINITION.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(CreateFormDefinitionMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }


    /**
     * 根据表单id获取表单数据
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext getFormDefinition(String tenantId, GetFormDefinition payload, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.FORM_SERVER_GET_FORM_DIFINITION.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(GetFormDefinitionByIdMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }

    /**
     * 根据表单id获取表单数据（异步）
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    @Override
    public void getFormDefinition(String tenantId, GetFormDefinition payload, MessageCallback messageCallback, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(payload).id(HandlerIdClientEnum.FORM_SERVER_GET_FORM_DIFINITION.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(GetFormDefinitionByIdMessage.class);
        messageSenderExecuter.sendMessage(message,messageCallback);
    }

    /**
     * 为表单封装信息（同步）
     * @param tenantId
     * @param formSubmitData
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext submitData(String tenantId, FormSubmitData formSubmitData, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().palyload(formSubmitData).id(HandlerIdClientEnum.FORM_SUBMIT_DATA.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(FormSubmitDataMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }



    /**
     * 获取表单列表
     * @param tenantId
     * @param currentUser
     * @return
     */
    @Override
    public MessageContext getFormList(String tenantId, CurrentUser currentUser) {
        Message message = MessageBuilder.builder().id(HandlerIdClientEnum.FORM_GET_MY_FORM.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build();
        return messageSenderExecuter.sendMessage(message);
    }


    @Override
    public MessageContext getFormDetail(String tenantId, GetFormDefinition getFormDefinition, CurrentUser currentUser){
        Message message = MessageBuilder.builder().palyload(getFormDefinition).id(HandlerIdClientEnum.FORM_ACTIVITI_DEFINITION_GET_FORM_DIFINITION.getId()).tenant(tenantId)
                .currentUser(currentUser.getUserId(), currentUser.getUserName())
                .build(GetFormDefinitionByIdMessage.class);
        return messageSenderExecuter.sendMessage(message);
    }
}
