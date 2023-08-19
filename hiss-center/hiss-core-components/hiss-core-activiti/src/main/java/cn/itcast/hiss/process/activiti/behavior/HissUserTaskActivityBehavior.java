package cn.itcast.hiss.process.activiti.behavior;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.process.activiti.service.ActivitiService;
import cn.itcast.hiss.process.activiti.service.UserTaskService;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.bpmn.behavior.VariablesCalculator;
import org.activiti.engine.impl.bpmn.behavior.VariablesPropagator;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.ExpressionManager;
import org.activiti.runtime.api.impl.MappingAwareUserTaskBehavior;

import java.util.*;

/*
 * @author miukoo
 * @description
 * @date 2023/5/26 10:05
 * @version 1.0
 **/
@Slf4j
public class HissUserTaskActivityBehavior extends MappingAwareUserTaskBehavior {

    private HissServerApperanceTemplate hissServerApperanceTemplate = SpringUtil.getBean("hissServerApperanceTemplate");

    private UserTaskService userTaskService = SpringUtil.getBean("hissUserTaskService");

    private ActivitiService activitiService = SpringUtil.getBean(ActivitiService.class);


    public HissUserTaskActivityBehavior(UserTask userTask, VariablesCalculator variablesCalculator, VariablesPropagator variablesPropagator) {
        super(userTask,variablesCalculator,variablesPropagator);
    }

    @Override
    public void trigger(DelegateExecution execution, String signalName, Object signalData) {
        System.out.println("====HissUserTaskActivityBehavior=====trigger======");
        super.trigger(execution, signalName, signalData);
    }

    @Override
    public void execute(DelegateExecution execution) {
        FlowElement currentFlowElement = execution.getCurrentFlowElement();
        //TODO 这里的代码需要优化 考虑责任链 或策略优化
        if (currentFlowElement instanceof UserTask) {
            //TODO 获取所有的流程变量
            UserTask currentUserTask = (UserTask) currentFlowElement;
            //候选人
            String assignee = currentUserTask.getAssignee();
            //候选用户
            List<String> candidateUsers = currentUserTask.getCandidateUsers();
            //候选组
            List<String> candidateGroups = currentUserTask.getCandidateGroups();
            //如果是以hiss_client开头的变量--则不请求客户端
            if (StrUtil.isNotEmpty(assignee)) {
                if (assignee.startsWith("hiss_client")) {
                    // 获取变量解析器
                    ExpressionManager expressionManager = Context.getProcessEngineConfiguration().getExpressionManager();
                    if (StrUtil.isNotEmpty(assignee)) {
                        Expression expression = expressionManager.createExpression(assignee);
                        String result = (String) expression.getValue(execution);
                        currentUserTask.setAssignee(result);
                    }
                    if (ObjectUtil.isNotNull(candidateUsers)) {
                        List<String> candidateUserList = new ArrayList<>();
                        //遍历所有的候选用户
                        for (String candidateUser : candidateUsers) {
                            Expression expression = expressionManager.createExpression(candidateUser);
                            String result = (String) expression.getValue(execution);
                            candidateUserList.add(result);
                        }
                        currentUserTask.setCandidateUsers(candidateUserList);
                    }
                    if (ObjectUtil.isNotNull(candidateGroups)) {
                        List<String> candidateUserList = new ArrayList<>();
                        //遍历所有的候选用户
                        for (String candidategroup : candidateGroups) {
                            Expression expression = expressionManager.createExpression(candidategroup);
                            String result = (String) expression.getValue(execution);
                            candidateUserList.add(result);
                        }
                        currentUserTask.setCandidateGroups(candidateUserList);
                    }
                }
            } else {
                //如果获取到的值不为空--说明就是
                if (StrUtil.isNotEmpty(assignee)) {
                    dealAssignee(execution, currentUserTask, assignee);
                }
                if (CollectionUtil.isNotEmpty(candidateUsers)) {
                    dealCandidateUsers(execution, currentUserTask, candidateUsers);
                }
                if (CollectionUtil.isNotEmpty(candidateGroups)) {
                    dealCandidateGroups(execution, currentUserTask, candidateGroups);
                }
            }
            //按照内置结果执行
            super.execute(execution);
        }
    }

    /**
     * 处理候选用户组
     *
     * @param execution
     * @param currentUserTask
     * @param candidateGroups
     */
    private void dealCandidateGroups(DelegateExecution execution, UserTask currentUserTask, List<String> candidateGroups) {
        Set<String> strings = commonDeal(execution, candidateGroups);
        currentUserTask.setCandidateGroups(new ArrayList<String>(strings));
    }

    /**
     * 处理候选组
     *
     * @param execution
     * @param currentFlowElement
     * @param candidateUsers
     */
    private void dealCandidateUsers(DelegateExecution execution, UserTask currentFlowElement, List<String> candidateUsers) {
        Set<String> strings = commonDeal(execution, candidateUsers);
        currentFlowElement.setCandidateUsers(new ArrayList<String>(strings));
    }


    /**
     * 处理执行人数据
     *
     * @param execution
     * @param currentUserTask
     * @param assignee
     */
    private void dealAssignee(DelegateExecution execution, UserTask currentUserTask, String assignee) {
        List<String> assigneeList = Arrays.asList(assignee);
        //判断是否要请求客户端
        Set<String> nameList = commonDeal(execution, assigneeList);
        String result = String.join(",", nameList);
        currentUserTask.setAssignee(result);
    }

    /**
     * 公共的处理UserTask 变量的方法
     *
     * @param execution
     * @param assignee
     * @return
     */
    private Set<String> commonDeal(DelegateExecution execution, List<String> assignee) {
        Set<String> resClientNames = new HashSet<String>();
        assignee.forEach(index -> {
            resClientNames.addAll(activitiService.doGetClientUserInfo(execution, index));
        });
        return resClientNames;
    }
}
