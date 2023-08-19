package cn.itcast.hiss.process.activiti.handler.sys.app;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.form.mapper.HissFormModelMapper;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.UserAppMessage;
import cn.itcast.hiss.message.sys.pojo.UserApp;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author: miukoo
 * @describe: 用户应用删除
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_UserAppDeleteHandler implements CmdHandler<UserAppMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HissFormModelMapper hissFormModelMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        UserApp userApp = (UserApp) params.getPalyload();
        HissUserApp dbApp = hissUserAppMapper.selectById(userApp.getId());
        if(dbApp!=null){
            if(dbApp.getUserId().equals(params.getMessageAuth().getCurrentUser().getUserId())||isAdmin(params)){
                String appId = dbApp.getAppId();
                Model model = repositoryService.createModelQuery().modelTenantId(appId).singleResult();
                if(model==null){
                    int count = hissFormModelMapper.selectAllByTenantIdInt(appId);
                    if(count==0){
                        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionTenantId(appId).singleResult();
                        if(processDefinition==null){
                            hissUserAppMapper.deleteById(dbApp.getId());
                            messageContext.addResultAndCount("msg","操作成功");
                        }else{
                            messageContext.addError("msg","存在相关流程定义，不允许删除");
                        }
                    }else{
                        messageContext.addError("msg","存在相关表单设计，不允许删除");
                    }
                }else{
                    messageContext.addError("msg","存在相关流程设计，不允许删除");
                }
            }else{
                messageContext.addError("msg","无权限操作");
            }
        }else{
            messageContext.addError("msg","删除的数据不存在或已经被删除");
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_USER_APP_DELETE.getId();
    }
}
