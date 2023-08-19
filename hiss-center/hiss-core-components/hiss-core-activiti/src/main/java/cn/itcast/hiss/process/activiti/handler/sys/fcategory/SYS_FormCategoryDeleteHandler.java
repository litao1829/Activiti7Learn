package cn.itcast.hiss.process.activiti.handler.sys.fcategory;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.form.mapper.HissFormCategoryMapper;
import cn.itcast.hiss.form.mapper.HissFormModelMapper;
import cn.itcast.hiss.form.pojo.HissFormCategory;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 表单分类删除
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_FormCategoryDeleteHandler implements CmdHandler<ProcessCategoryMessage> {

    @Autowired
    private HissFormCategoryMapper hissFormCategoryMapper;
    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private HissFormModelMapper hissFormModelMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessCategory processCategory = (ProcessCategory) params.getPalyload();
        HissFormCategory dbCategory = hissFormCategoryMapper.selectById(processCategory.getId());
        if(dbCategory!=null){
            // 修改权限判断
            boolean canEdit = isAdmin(params);
            if(!canEdit){
                List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
                canEdit = userAppIdsList.contains(dbCategory.getUserAppId());
            }
            if(canEdit){
                long count = hissFormModelMapper.countCategoryId(dbCategory.getId(),dbCategory.getUserAppId());
                if(count==0){
                    hissFormCategoryMapper.deleteById(processCategory.getId());
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
        return HandlerIdClientEnum.SYS_FORM_CATEGORY_DELETE.getId();
    }
}
