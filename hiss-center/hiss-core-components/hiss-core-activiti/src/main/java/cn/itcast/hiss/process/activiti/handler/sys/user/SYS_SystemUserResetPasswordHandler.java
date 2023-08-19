package cn.itcast.hiss.process.activiti.handler.sys.user;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 *
 * @author: miukoo
 * @describe: 重置用户密码
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_SystemUserResetPasswordHandler implements CmdHandler<SystemUserMessage> {

    @Autowired
    private HissSystemUserMapper hissSystemUserMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        SystemUser systemUser = (SystemUser) params.getPalyload();
        HissSystemUser hissSystemUser = new HissSystemUser();
        if(systemUser.getId()!=null){
            HissSystemUser dbUser = hissSystemUserMapper.selectById(systemUser.getId());
            if(dbUser!=null){
                if(dbUser.getId().equals(params.getMessageAuth().getCurrentUser().getUserId())||isAdmin(params)){
                    hissSystemUser.setPassword(BCrypt.hashpw(SYS_SystemUserAddOrUpdateHandler.DEFAULT_PASSWORD,dbUser.getSalt()));
                    hissSystemUser.setId(dbUser.getId());
                    hissSystemUserMapper.updateById(hissSystemUser);
                    messageContext.addResultAndCount("msg","操作成功");
                }
            }else{
                messageContext.addError("msg","无权限操作");
            }
        }else{
            messageContext.addError("msg","修改的数据不存在或已经被删除");
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_SYSTEM_USER_RESET_PASSWORD.getId();
    }
}
