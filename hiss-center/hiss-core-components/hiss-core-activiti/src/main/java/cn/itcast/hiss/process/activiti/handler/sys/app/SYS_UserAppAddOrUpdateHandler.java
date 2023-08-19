package cn.itcast.hiss.process.activiti.handler.sys.app;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.UserAppMessage;
import cn.itcast.hiss.message.sys.pojo.UserApp;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 *
 * @author: miukoo
 * @describe: 用户应用新增
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_UserAppAddOrUpdateHandler implements CmdHandler<UserAppMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        UserApp userApp = (UserApp) params.getPalyload();
        HissUserApp hissUserApp = new HissUserApp();
        BeanUtils.copyProperties(userApp,hissUserApp);
        if(userApp.getId()!=null){
            HissUserApp dbApp = hissUserAppMapper.selectById(userApp.getId());
            if(dbApp!=null){
                if(dbApp.getUserId().equals(params.getMessageAuth().getCurrentUser().getUserId())||isAdmin(params)){
                    if(StrUtil.isNotEmpty(hissUserApp.getAppId())){
                        int count = hissUserAppMapper.checkID(dbApp.getId(), hissUserApp.getAppId());
                        if(count!=0){
                            messageContext.addError("msg","ID已经被占用");
                            return;
                        }
                    }
                    hissUserApp.setCreatedTime(null);
                    hissUserApp.setUpdatedTime(new Date());
                    hissUserAppMapper.updateById(hissUserApp);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","无权限操作");
                }
            }else{
                messageContext.addError("msg","修改的数据不存在或已经被删除");
            }
        }else{
            // 新增
            if(StrUtil.isNotEmpty(hissUserApp.getAppId())){
                int count = hissUserAppMapper.checkID(null, hissUserApp.getAppId());
                if(count==0){
                    hissUserApp.setUserId(params.getMessageAuth().getCurrentUser().getUserId());
                    hissUserApp.setCreatedTime(new Date());
                    hissUserApp.setStatus(1);
                    hissUserAppMapper.insert(hissUserApp);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","ID已经被占用");
                }
            }else{
                messageContext.addError("msg","应用ID不能为空");
            }
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_USER_APP_ADD_OR_UPDATE.getId();
    }
}
