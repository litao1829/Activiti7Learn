//package cn.itcast.hiss.process.activiti.handler.sys;
//
//import cn.itcast.hiss.api.client.task.PracticeTask;
//import cn.itcast.hiss.cmd.handler.CmdHandler;
//import cn.itcast.hiss.handler.HandlerIdClientEnum;
//import cn.itcast.hiss.message.Message;
//import cn.itcast.hiss.message.MessageContext;
//import cn.itcast.hiss.message.sender.practice.PracticeTaskMessage;
//import org.activiti.engine.RepositoryService;
//import org.activiti.engine.repository.Model;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
////@Component
//public class SYS_OnboardingprocessHandler implements CmdHandler<PracticeTaskMessage> {
//
//    @Autowired
//    private RepositoryService repositoryService;
//
//    @Override
//    public void invoke(Message params, MessageContext messageContext) {
//        //创建modal进行保存
//        Model model = repositoryService.newModel();
//        PracticeTask practiceTask = (PracticeTask)params.getPalyload();
//        model.setName(practiceTask.getName());
//        model.setCategory(practiceTask.getCategory());
//        //保存modal获取ID
//        repositoryService.saveModel(model);
//        repositoryService.addModelEditorSource(model.getId(),practiceTask.getContent().getBytes());
//        Map<String,Object> result = new HashMap<>();
//        result.put("modelId",model.getId());
//        messageContext.addResultAndCount("data", result);
//    }
//
//    @Override
//    public String getId() {
//        return HandlerIdClientEnum.FLOW_M_SAVE_MODEL_FOR_DEV.getId();
//    }
//
//    @Override
//    public boolean isAdmin(Message params) {
//        return CmdHandler.super.isAdmin(params);
//    }
//}
