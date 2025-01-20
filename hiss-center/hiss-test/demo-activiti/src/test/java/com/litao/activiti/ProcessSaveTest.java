package com.litao.activiti;

import cn.hutool.core.io.FileUtil;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProcessSaveTest {
    @Autowired
    RepositoryService repositoryService;

    @ParameterizedTest
    @ValueSource(strings = {"C:\\Users\\Acer\\Desktop\\bpmn-01.bpmn.xml"})
    public void testSaveFromFile(String file){
        Model model = repositoryService.newModel();//快速创建一个流程模型的对象
        model.setName("单人审批请假流程");//名称
        model.setCategory("学习");//分类
        repositoryService.saveModel(model);//保存bpmn设计好的流程文件的方法
        byte[] bytes = FileUtil.readBytes(file);
        repositoryService.addModelEditorSource(model.getId(),bytes);//保存流程图的xml文件内容到数据库
    }
}
