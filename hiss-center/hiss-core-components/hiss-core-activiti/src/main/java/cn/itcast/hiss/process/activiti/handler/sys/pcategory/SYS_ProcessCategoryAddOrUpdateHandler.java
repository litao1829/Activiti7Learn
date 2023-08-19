package cn.itcast.hiss.process.activiti.handler.sys.pcategory;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.process.activiti.mapper.HissProcessCategoryMapper;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissProcessCategory;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 流程分类新增或修改
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_ProcessCategoryAddOrUpdateHandler implements CmdHandler<ProcessCategoryMessage> {

    @Autowired
    private HissProcessCategoryMapper hissProcessCategoryMapper;
    @Autowired
    private HissUserAppMapper hissUserAppMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessCategory processCategory = (ProcessCategory) params.getPalyload();
        HissProcessCategory hissProcessCategory = new HissProcessCategory();
        BeanUtils.copyProperties(processCategory,hissProcessCategory);
        if(hissProcessCategory.getId()!=null){
            HissProcessCategory dbCategory = hissProcessCategoryMapper.selectById(hissProcessCategory.getId());
            if(dbCategory!=null){
                // 修改权限判断
                boolean canEdit = isAdmin(params);
                if(!canEdit){
                    List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
                    canEdit = userAppIdsList.contains(dbCategory.getUserAppId());
                }
                if(canEdit){
                    if(StrUtil.isNotEmpty(hissProcessCategory.getName())){
                        int count = hissProcessCategoryMapper.checkName(dbCategory.getId(), hissProcessCategory.getName(),dbCategory.getUserAppId());
                        if(count!=0){
                            messageContext.addError("msg","分类名称重复");
                            return;
                        }
                    }
                    hissProcessCategory.setCreatedTime(null);
                    hissProcessCategoryMapper.updateById(hissProcessCategory);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","无权限操作");
                }
            }else{
                messageContext.addError("msg","修改的数据不存在或已经被删除");
            }
        }else{
            // 新增
            if(StrUtil.isNotEmpty(hissProcessCategory.getName())&&StrUtil.isNotEmpty(hissProcessCategory.getUserAppId())){
                int count = hissProcessCategoryMapper.checkName(null, hissProcessCategory.getName(),hissProcessCategory.getUserAppId());
                if(count==0){
                    hissProcessCategory.setCreatedTime(new Date());
                    hissProcessCategory.setOrd(System.currentTimeMillis());
                    hissProcessCategoryMapper.insert(hissProcessCategory);
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
        return HandlerIdClientEnum.SYS_PROCESSS_CATEGORY_ADD_OR_UPDATE.getId();
    }
}
