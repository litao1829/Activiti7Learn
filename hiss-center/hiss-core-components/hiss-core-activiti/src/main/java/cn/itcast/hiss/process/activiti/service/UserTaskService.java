package cn.itcast.hiss.process.activiti.service;

import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.server.common.CreateUser;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.common.SystemConstant;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * UserTaskService
 *
 * @author: wgl
 * @describe: 用户任务业务层
 * @date: 2022/12/28 10:10
 */
public class UserTaskService {

    @Autowired
    TaskService taskService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    HistoryService historyService;

    /**
     * 获取上一个节点的执行人
     *
     * @param execution
     * @return
     */
    public UserInfo getBeforeNodeUserInfo(DelegateExecution execution) {
        UserInfo userInfo = new UserInfo();
        String processInstanceId = execution.getProcessInstanceId();
        // 查询历史活动实例，按照执行时间降序排序
        List<HistoricActivityInstance> activityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricActivityInstanceStartTime()
                .desc()
                .list();
        // 如果找不到上一个用户任务节点，查询流程发起人信息
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();
        CreateUser createUser = new CreateUser();
        if (processInstance != null) {
            Map variables = runtimeService.getVariables(processInstance.getId());
            // 根据 startedBy 获取流程发起人信息，填充到 UserInfo 对象中并返回
            createUser.setCreateUserId(variables.get(SystemConstant.TASK_VARIABLES_CREATE_USERID).toString());
            createUser.setCreateName(variables.get(SystemConstant.TASK_VARIABLES_CREATE_USERNAME).toString());
            createUser.setCreateTime(processInstance.getStartTime());
        }else{
            //说明是第一个节点，还未创建流程实例
            createUser.setCreateUserId(execution.getVariable(SystemConstant.TASK_VARIABLES_CREATE_USERID).toString());
            createUser.setCreateName(execution.getVariable(SystemConstant.TASK_VARIABLES_CREATE_USERNAME).toString());
        }
        userInfo.setCreateUser(createUser);
        // 查找上一个用户任务节点的执行人
        for (HistoricActivityInstance activityInstance : activityInstances) {
            if (activityInstance.getActivityType().equals("userTask")) {
                String taskDefinitionKey = activityInstance.getActivityId();
                Task task = taskService.createTaskQuery()
                        .processInstanceId(processInstanceId)
                        .taskDefinitionKey(taskDefinitionKey)
                        .singleResult();
                if (task != null) {
                    String assignee = task.getAssignee();
                    // 根据 assignee 获取用户信息，填充到 UserInfo 对象中并返回
                    userInfo.setLastAssigne(assignee);
                    userInfo.setLastTaskId(task.getId());
                    userInfo.setLastTaskName(task.getName());
                    return userInfo;
                }
            }
        }
        return userInfo;
    }
}
