package com.litao.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessRunTest {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RuntimeService runtimeService;

    /**
     * 流程部署测试
     */
    @Test
    public void testDeployment(){
        String modelId = "604a87d6-d72a-11ef-bc6c-005056c00001";
        String xmlName = "20.bpmn20.xml";
        //查询模型基本信息
        Model model = repositoryService.getModel(modelId);
        //读取模型的xml内容
        byte[] xmlContent = repositoryService.getModelEditorSource(modelId);
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment()
                .category(model.getCategory())
                .name(model.getName())
                .addBytes(xmlName, xmlContent);

        //部署（把原模型文件复制一份）
        Deployment deployment = deploymentBuilder.deploy();

        //把部署的ID回显到模型表
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }

    /**
     * 运行测试
     */
    @ParameterizedTest
    @ValueSource(strings = {"hiss_process_12_1736947305758"})
    public void testRun(String procdefKey){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(procdefKey);
        System.out.println(processInstance.getId());
    }
}
