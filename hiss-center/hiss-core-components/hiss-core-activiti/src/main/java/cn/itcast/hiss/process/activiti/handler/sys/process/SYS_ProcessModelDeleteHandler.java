package cn.itcast.hiss.process.activiti.handler.sys.process;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessModelMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 用户流程删除
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_ProcessModelDeleteHandler implements CmdHandler<ProcessModelMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessModel processModel = (ProcessModel) params.getPalyload();
        Model model = repositoryService.createModelQuery().modelId(processModel.getId()).singleResult();
        if(model!=null){
            boolean canEdit = isAdmin(params);
            if(!canEdit){
                List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
                canEdit = userAppIdsList.contains(model.getTenantId());
            }
            if(canEdit){
                if(StrUtil.isNotEmpty(model.getDeploymentId())){
                    long count = runtimeService.createProcessInstanceQuery().deploymentId(model.getTenantId()).count();
                    if(count==0){
                        Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(model.getDeploymentId()).singleResult();
                        if(deployment!=null){
                            repositoryService.deleteDeployment(deployment.getId(),true);
                        }
                    }else{
                        messageContext.addError("msg","存在相关流程实例，不允许删除");
                        return;
                    }
                }
                repositoryService.deleteModel(model.getId());
                messageContext.addResultAndCount("msg","操作成功");
            }else{
                messageContext.addError("msg","无权限操作");
            }
        }else{
            messageContext.addError("msg","删除的数据不存在或已经被删除");
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_PROCESS_MODEL_DELETE.getId();
    }
}
