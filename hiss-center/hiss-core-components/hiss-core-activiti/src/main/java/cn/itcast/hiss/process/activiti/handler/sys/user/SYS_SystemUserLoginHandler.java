package cn.itcast.hiss.process.activiti.handler.sys.user;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSigner;
import cn.hutool.jwt.signers.JWTSignerUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.PageResponseResult;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author: miukoo
 * @describe: 用户列表查询处理器
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_SystemUserLoginHandler implements CmdHandler<SystemUserMessage> {

    @Autowired
    private HissSystemUserMapper hissSystemUserMapper;

    private String tokenKey = "HISS_PROCESS_FLOW";

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        SystemUser systemUser = (SystemUser) params.getPalyload();
        if(StrUtil.isNotEmpty(systemUser.getUsername())&&StrUtil.isNotEmpty(systemUser.getPassword())){
            HissSystemUser hissSystemUser = hissSystemUserMapper.selectByUsername(systemUser.getUsername());
            if(hissSystemUser!=null){
                if(BCrypt.checkpw(systemUser.getPassword(),hissSystemUser.getPassword())){
                    if(hissSystemUser.getStatus()==1){
                        Map<String,Object> map = new HashMap<>();
                        map.put("id",hissSystemUser.getId());
                        map.put("name",hissSystemUser.getName());
                        String token = JWTUtil.createToken(map, tokenKey.getBytes());
                        map.put("token",token);
                        messageContext.addResultAndCount("result",map);
                    }else{
                        messageContext.addError("msg","该账户被限制使用");
                    }
                }else{
                    messageContext.addError("msg","用户名或密码不正确");
                }
            }else{
                messageContext.addError("msg","用户名或密码不正确");
            }
        }else{
            messageContext.addError("msg","用户名和密码是必传参数");
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_SYSTEM_USER_LOGIN.getId();
    }
}
