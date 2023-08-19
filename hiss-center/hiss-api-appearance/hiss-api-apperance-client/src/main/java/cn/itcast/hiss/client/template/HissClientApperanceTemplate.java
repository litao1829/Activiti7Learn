package cn.itcast.hiss.client.template;

import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.api.client.common.PageInfo;
import cn.itcast.hiss.api.client.form.*;
import cn.itcast.hiss.api.client.processdefinition.DeleteProcessDefinition;
import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.api.client.processinstance.CreateProcessInstance;
import cn.itcast.hiss.api.client.processinstance.HissProcessInstance;
import cn.itcast.hiss.api.client.task.*;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.MessageCallback;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessInstance;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;

import java.util.List;

/*
 * @author miukoo
 * @description 暴露的给上层使用的接口类
 * 1、flowM 流程定义相关
 * 2、flowB 流程办理相关
 * @date 2023/5/24 19:00
 * @version 1.0
 **/
public interface HissClientApperanceTemplate {

    /**
     * 查询租户的流程分类列表【使用中】
     *
     * @param tennetId
     * @param processCategory
     * @param currentUser
     * @return
     */
    public MessageContext listProcessCategory(String tennetId, ProcessCategory processCategory, CurrentUser currentUser);

    /**
     * 查询租户的表单分类列表【使用中】
     *
     * @param tennetId
     * @param processCategory
     * @param currentUser
     * @return
     */
    public MessageContext listFormCategory(String tennetId, ProcessCategory processCategory, CurrentUser currentUser);

    /**
     * 查询租户的流程分类下的流程列表【使用中】
     *
     * @param tennetId
     * @param processModel
     * @param currentUser
     * @return
     */
    public MessageContext listCategoryProcess(String tennetId, ProcessModel processModel, CurrentUser currentUser);

    /**
     * 查询个申请的流程列表【使用中】
     *
     * @param tennetId
     * @param processInstance
     * @param currentUser
     * @return
     */
    public MessageContext listApplayProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser);

    /**
     * 删除个申请的流程列表【使用中】
     *
     * @param tennetId
     * @param processInstance
     * @param currentUser
     * @return
     */
    public MessageContext deleteApplayProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser);

    /**
     * 删除流程设计【使用中】
     * 必须传adminId
     * @param tennetId
     * @param processModel
     * @param currentUser
     * @return
     */
    public MessageContext deleteProcess(String tennetId, ProcessModel processModel, CurrentUser currentUser);

    /**
     * 查询个人的办理流程列表【使用中】
     *
     * @param tennetId
     * @param processInstance
     * @param currentUser
     * @return
     */
    public MessageContext listHandleProcess(String tennetId, ProcessInstance processInstance, CurrentUser currentUser);

    /**
     * 删除表单列表【使用中】
     *
     * @param tennetId
     * @param processModel
     * @param currentUser
     * @return
     */
    public MessageContext deleteFormModel(String tennetId, ProcessModel processModel, CurrentUser currentUser);

    /**
     * 查询表单列表【使用中】
     *
     * @param tennetId
     * @param processModel
     * @param currentUser
     * @return
     */
    public MessageContext listFormModel(String tennetId, ProcessModel processModel, CurrentUser currentUser);

    /**
     * 查询流程实例列表【使用中】
     *
     * @param tennetId
     * @param processInstance
     * @param currentUser
     * @return
     */
    public MessageContext listProcessInstance(String tennetId, ProcessInstance processInstance, CurrentUser currentUser);

    /**
     * 删除流程实例列表【使用中】
     *
     * @param tennetId
     * @param processInstance
     * @param currentUser
     * @return
     */
    public MessageContext deleteProcessInstance(String tennetId, ProcessInstance processInstance, CurrentUser currentUser);

    /**
     * 启动业务流程【使用中】
     *
     * @param tennetId
     * @param processDesignModel
     * @param currentUser
     * @return
     */
    public MessageContext startBisPreProcess(String tennetId, ProcessDesignModel processDesignModel, CurrentUser currentUser);

    /**
     * 获取我的流程文件
     *
     * @param payload
     */
    public MessageContext getProcessDefination(String tennetId, PageInfo payload, CurrentUser currentUser);

    /**
     * 获取我的流程
     *
     * @param payload
     */
    public void getProcessDefination(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 删除流程定义
     *
     * @param tennetId
     * @param payload
     * @return
     */
    public MessageContext deleteProcessDefinitionMessage(String tennetId, DeleteProcessDefinition payload, CurrentUser currentUser);

    /**
     * 删除流程定义
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    public void deleteProcessDefinitionMessage(String tennetId, DeleteProcessDefinition payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 创建流程实例
     *
     * @param tennetId
     * @param payload
     * @return
     */
    public MessageContext createProcessInstance(String tennetId, CreateProcessInstance payload, CurrentUser currentUser);

    /**
     * 创建流程实例
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    public void createProcessInstance(String tennetId, CreateProcessInstance payload, MessageCallback messageCallback, CurrentUser currentUser);


    /**
     * 创建流程实例
     *
     * @param tennetId
     * @param payload
     * @return
     */
    public MessageContext getProcessInstance(String tennetId, PageInfo payload, CurrentUser currentUser);

    /**
     * 创建流程实例
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    public void getProcessInstance(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser);


    /**
     * 开启流程
     *
     * @param tennetId
     * @param payload
     * @return
     */
    public MessageContext startProcessInstance(String tennetId, HissProcessInstance payload, CurrentUser currentUser);

    /**
     * 创建流程实例
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     */
    public void startProcessInstance(String tennetId, HissProcessInstance payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 查询我的待办
     *
     * @param tennetId
     * @param payload
     * @param currentUser
     */
    public MessageContext getMyTask(String tennetId, PageInfo payload, CurrentUser currentUser);

    /**
     * 查询我的待办
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    public void getMyTask(String tennetId, PageInfo payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 完成任务
     *
     * @param tennetId
     * @param payload
     * @param currentUser
     */
    public MessageContext approveTask(String tennetId, ApproveTask payload, CurrentUser currentUser);

    /**
     * 完成任务
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    public void approveTask(String tennetId, ApproveTask payload, MessageCallback messageCallback, CurrentUser currentUser);


    /**
     * 发送客户端所有变量到服务端
     *
     * @param tenantId
     * @param payload
     */
    public MessageContext sendVariable(String tenantId, List<HissVariable> payload);

    /**
     * 获取在客户端的所有上报的变量
     *
     * @param tenantId
     * @return
     */
    MessageContext getClientVariable(String tenantId);

    /**
     * 不同意任务
     *
     * @param tennetId
     * @param payload
     * @param messageCallback
     * @param currentUser
     */
    public void rejectTask(String tennetId, RejectTask payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 不同意任务
     *
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    public MessageContext rejectTask(String tenantId, RejectTask payload, CurrentUser currentUser);

    MessageContext rollbackTask(String tenantId, AnyJumpTask payload, CurrentUser currentUser);

    void rollbackTask(String tenantId, AnyJumpTask payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 委派任务(同步)
     *
     * @param tenantId
     * @param taskAssigned
     * @param user
     * @return
     */
    public MessageContext delegateTask(String tenantId, DelegateTask taskAssigned, CurrentUser user);

    /**
     * 委派任务（异步）
     *
     * @param tenantId
     * @param taskAssigned
     * @param user
     * @return
     */
    public void delegateTask(String tenantId, DelegateTask taskAssigned, MessageCallback messageCallback, CurrentUser user);


    /**
     * 后加签（异步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public void afterAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser user);


    /**
     * 后加签（同步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext afterAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser user);


    /**
     * 前加签（异步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public void beforeAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser user);

    /**
     * 前加签（同步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext beforeAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser user);


    /**
     * 并行加签（异步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public void parallelAddMultiInstanceTask(String tenantId, AddExecutionTask payload, MessageCallback messageCallback, CurrentUser user);

    /**
     * 并行加签（同步）
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext parallelAddMultiInstanceTask(String tenantId, AddExecutionTask payload, CurrentUser user);

    /**
     * 拾取任务
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext claimTask(String tenantId, HissTask payload, CurrentUser user);

    /**
     * 拾取任务
     *
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param user
     */
    public void claimTask(String tenantId, HissTask payload, MessageCallback messageCallback, CurrentUser user);


    /**
     * 信号消息(异步)
     *
     * @param tenantId
     * @param payload
     * @param messageCallback
     * @param user
     */
    public void tiggerMessage(String tenantId, TiggerTask payload, MessageCallback messageCallback, CurrentUser user);

    /**
     * 信号消息(同步)
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext tiggerMessage(String tenantId, TiggerTask payload, CurrentUser user);


    /**
     * 创建表单
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public MessageContext createForm(String tenantId, CreateFormDefinition payload, CurrentUser user);

    /**
     * 创建表单
     *
     * @param tenantId
     * @param payload
     * @param user
     * @return
     */
    public void createForm(String tenantId, CreateFormDefinition payload, MessageCallback messageCallback, CurrentUser user);


    /**
     * 获取表单定义
     *
     * @param tenantId
     * @param payload
     * @param currentUser
     * @return
     */
    MessageContext getFormDefinition(String tenantId, GetFormDefinition payload, CurrentUser currentUser);


    void getFormDefinition(String tenantId, GetFormDefinition payload, MessageCallback messageCallback, CurrentUser currentUser);

    /**
     * 提交数据
     *
     * @param tenantId
     * @param formSubmitData
     * @param user
     * @return
     */
    public MessageContext submitData(String tenantId, FormSubmitData formSubmitData, CurrentUser user);


    public MessageContext getFormList(String tenantId, CurrentUser currentUser);

    MessageContext getFormDetail(String tenantId, GetFormDefinition getFormDefinition, CurrentUser currentUser);
}
