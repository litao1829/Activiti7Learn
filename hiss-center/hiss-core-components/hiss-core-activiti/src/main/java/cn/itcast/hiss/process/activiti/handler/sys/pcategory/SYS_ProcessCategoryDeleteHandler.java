package cn.itcast.hiss.process.activiti.handler.sys.pcategory;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.process.activiti.mapper.HissProcessCategoryMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissProcessCategory;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 流程分类删除
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_ProcessCategoryDeleteHandler implements CmdHandler<ProcessCategoryMessage> {

    @Autowired
    private HissProcessCategoryMapper hissProcessCategoryMapper;
    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private RepositoryService repositoryService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessCategory processCategory = (ProcessCategory) params.getPalyload();
        HissProcessCategory dbCategory = hissProcessCategoryMapper.selectById(processCategory.getId());
        if(dbCategory!=null){
            // 修改权限判断
            boolean canEdit = isAdmin(params);
            if(!canEdit){
                List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
                canEdit = userAppIdsList.contains(dbCategory.getUserAppId());
            }
            if(canEdit){
                long count = repositoryService.createModelQuery().modelCategory("" + dbCategory.getId()).modelTenantId(processCategory.getUserAppId()).count();
                if(count==0){
                    hissProcessCategoryMapper.deleteById(processCategory.getId());
                    messageContext.addResultAndCount("msg","操作成功");
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
        return HandlerIdClientEnum.SYS_PROCESSS_CATEGORY_DELETE.getId();
    }
}
