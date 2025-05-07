package com.litao.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
public class ProcessHandleTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 完成填写清单
     * @param taskId
     */
    @ParameterizedTest
    @ValueSource(strings = {"d33812d8-f50a-11ef-ad7b-005056c00001"})
    public void testCompleteNode2(String taskId){
        //接受表单信息，并保存到数据库
        Map<String,Object> variables = new HashMap<>();
        variables.put("userName","张三");
        variables.put("startDate","2025-1-30");
        variables.put("days",7);
        variables.put("reason","过年回家");
        //告诉Activiti当前任务已经完成，可以执行下一步
        taskService.complete(taskId,variables);

    }

    /**
     * 同意
     */
    @ParameterizedTest
    @ValueSource(strings = {"7e3ae8ea-f50b-11ef-bc3e-005056c00001"})
    public void testAgree(String taskId){
        // 把【同意】看做是填写的【审批表单（包括：审批结果、审批意见）】中的approvalStatus字段
        Map<String, Object> variables = new HashMap<>();
        variables.put("approvalStatus","同意");//
        variables.put("approvalNote","同意的备注意见：张三，你把工作交接清楚再走");
        // 标记任务完成
        taskService.complete(taskId, variables);
    }

    /**
     * 不同意，既拒绝
     */
    @ParameterizedTest
    @ValueSource(strings = {"c979ec1a-def1-11ef-acca-005056c00001"})
    public void testReject(String processInstanceId){
        String reason = "这里是不同意的理由";
        // 把【同意】看做是填写的【审批表单（包括：审批结果、审批意见）】中的approvalStatus字段
        Map<String, Object> variables = new HashMap<>();
        variables.put("approvalStatus","不同意");
        variables.put("approvalNote",reason);
        //记录流程变量
        runtimeService.setVariables(processInstanceId, variables);
        //不同意：就是结束当前的流程实例
        runtimeService.deleteProcessInstance(processInstanceId,reason);
    }
}
