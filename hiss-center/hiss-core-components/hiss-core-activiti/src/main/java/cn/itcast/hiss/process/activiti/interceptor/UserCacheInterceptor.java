package cn.itcast.hiss.process.activiti.interceptor;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.service.ClientUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 异步去缓存当前请求头中的用户信息
 * @date 2023/7/5 20:56
 * @version 1.0
 **/
@Order(500)
@Component
public class UserCacheInterceptor extends CmdInterceptor {

    @Autowired
    ClientUserService clientUserService;

    public UserCacheInterceptor(){
        setAsync(true);
    }

    @Override
    public void invoke(MessageContext messageContext, Message message) {
        if(message!=null&&message.getMessageAuth()!=null){
            MessageAuth messageAuth = message.getMessageAuth();
            CurrentUser currentUser = messageAuth.getCurrentUser();
            if(currentUser!=null){
                String userId = currentUser.getUserId();
                String userName = currentUser.getUserName();
                String tenant = messageAuth.getTenant();
                if(StrUtil.isNotEmpty(userId)&&StrUtil.isNotEmpty(userName)&&StrUtil.isNotEmpty(tenant)){
                    clientUserService.cacheUserName(tenant,userId,userName);
                }
            }
        }
    }

}
