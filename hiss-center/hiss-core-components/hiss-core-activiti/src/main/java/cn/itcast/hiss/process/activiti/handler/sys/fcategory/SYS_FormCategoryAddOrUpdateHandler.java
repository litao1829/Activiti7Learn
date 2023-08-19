package cn.itcast.hiss.process.activiti.handler.sys.fcategory;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.form.mapper.HissFormCategoryMapper;
import cn.itcast.hiss.form.pojo.HissFormCategory;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 表单分类新增或修改
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_FormCategoryAddOrUpdateHandler implements CmdHandler<ProcessCategoryMessage> {

    @Autowired
    private HissFormCategoryMapper hissFormCategoryMapper;
    @Autowired
    private HissUserAppMapper hissUserAppMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessCategory processCategory = (ProcessCategory) params.getPalyload();
        HissFormCategory hissFormCategory = new HissFormCategory();
        BeanUtils.copyProperties(processCategory,hissFormCategory);
        if(hissFormCategory.getId()!=null){
            HissFormCategory dbCategory = hissFormCategoryMapper.selectById(hissFormCategory.getId());
            if(dbCategory!=null){
                // 修改权限判断
                boolean canEdit = isAdmin(params);
                if(!canEdit){
                    List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
                    canEdit = userAppIdsList.contains(dbCategory.getUserAppId());
                }
                if(canEdit){
                    if(StrUtil.isNotEmpty(hissFormCategory.getName())){
                        int count = hissFormCategoryMapper.checkName(dbCategory.getId(), hissFormCategory.getName(),dbCategory.getUserAppId());
                        if(count!=0){
                            messageContext.addError("msg","分类名称重复");
                            return;
                        }
                    }
                    hissFormCategory.setCreatedTime(null);
                    hissFormCategoryMapper.updateById(hissFormCategory);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","无权限操作");
                }
            }else{
                messageContext.addError("msg","修改的数据不存在或已经被删除");
            }
        }else{
            // 新增
            if(StrUtil.isNotEmpty(hissFormCategory.getName())&&StrUtil.isNotEmpty(hissFormCategory.getUserAppId())){
                int count = hissFormCategoryMapper.checkName(null, hissFormCategory.getName(),hissFormCategory.getUserAppId());
                if(count==0){
                    hissFormCategory.setCreatedTime(new Date());
                    hissFormCategory.setOrd(System.currentTimeMillis());
                    hissFormCategoryMapper.insert(hissFormCategory);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","分类名称重复");
                }
            }else{
                messageContext.addError("msg","缺失名称或AppId参数");
            }
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_FORM_CATEGORY_ADD_OR_UPDATE.getId();
    }
}
