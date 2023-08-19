package cn.itcast.hiss.process.activiti.handler.sys.user;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.UserAppMessage;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.message.sys.pojo.UserApp;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 *
 * @author: miukoo
 * @describe: 用户新增
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_SystemUserAddOrUpdateHandler implements CmdHandler<SystemUserMessage> {

    @Autowired
    private HissSystemUserMapper hissSystemUserMapper;

    public final static String DEFAULT_PASSWORD = "123456";

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        SystemUser systemUser = (SystemUser) params.getPalyload();
        HissSystemUser hissSystemUser = new HissSystemUser();
        BeanUtils.copyProperties(systemUser,hissSystemUser);
        if(systemUser.getId()!=null){
            HissSystemUser dbUser = hissSystemUserMapper.selectById(systemUser.getId());
            if(dbUser!=null){
                if(dbUser.getId().equalsIgnoreCase(HissProcessConstants.ADMIN_ID)){
                    messageContext.addError("msg","超管数据不允许操作");
                    return;
                }
                if(dbUser.getId().equals(params.getMessageAuth().getCurrentUser().getUserId())||isAdmin(params)){
                    if(StrUtil.isNotEmpty(hissSystemUser.getUsername())){
                        int count = hissSystemUserMapper.checkUsername(dbUser.getId(), hissSystemUser.getUsername());
                        if(count!=0){
                            messageContext.addError("msg","账户名已经被占用");
                            return;
                        }
                    }
                    if(StrUtil.isNotEmpty(hissSystemUser.getPassword())){//修改密码
                        hissSystemUser.setPassword(BCrypt.hashpw(hissSystemUser.getPassword(),dbUser.getSalt()));
                    }
                    hissSystemUser.setCreatedTime(null);
                    hissSystemUserMapper.updateById(hissSystemUser);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","无权限操作");
                }
            }else{
                messageContext.addError("msg","修改的数据不存在或已经被删除");
            }
        }else{
            // 新增
            if(StrUtil.isNotEmpty(hissSystemUser.getUsername())){
                int count = hissSystemUserMapper.checkUsername(null, hissSystemUser.getUsername());
                if(count==0){
                    hissSystemUser.setCreatedTime(new Date());
                    hissSystemUser.setStatus(1);
                    hissSystemUser.setSalt(BCrypt.gensalt());
                    if(StrUtil.isEmpty(hissSystemUser.getPassword())){
                        hissSystemUser.setPassword(SYS_SystemUserAddOrUpdateHandler.DEFAULT_PASSWORD);
                    }
                    hissSystemUser.setPassword(BCrypt.hashpw(hissSystemUser.getPassword(),hissSystemUser.getSalt()));
                    hissSystemUserMapper.insert(hissSystemUser);
                    messageContext.addResultAndCount("msg","操作成功");
                }else{
                    messageContext.addError("msg","账户名已经被占用");
                }
            }else{
                messageContext.addError("msg","用户和密码不能为空");
            }
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_SYSTEM_USER_ADD_OR_UPDATE.getId();
    }
}
