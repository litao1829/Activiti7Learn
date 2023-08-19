package cn.itcast.hiss.process.activiti.handler.sys.user;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author: miukoo
 * @describe: 用户删除
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_SystemUserDeleteHandler implements CmdHandler<SystemUserMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private HissSystemUserMapper hissSystemUserMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        SystemUser systemUser = (SystemUser) params.getPalyload();
        HissSystemUser dbUser = hissSystemUserMapper.selectById(systemUser.getId());
        if(dbUser!=null){
            if(dbUser.getId().equalsIgnoreCase(HissProcessConstants.ADMIN_ID)){
                messageContext.addError("msg","超管数据不允许操作");
                return;
            }
            if(dbUser.getId().equals(params.getMessageAuth().getCurrentUser().getUserId())||isAdmin(params)){
                int count = hissUserAppMapper.userAppCount(systemUser.getId());
                if(count==0){
                    hissSystemUserMapper.deleteById(dbUser.getId());
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","存在相关应用，不允许删除");
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
        return HandlerIdClientEnum.SYS_SYSTEM_USER_DELETE.getId();
    }
}
