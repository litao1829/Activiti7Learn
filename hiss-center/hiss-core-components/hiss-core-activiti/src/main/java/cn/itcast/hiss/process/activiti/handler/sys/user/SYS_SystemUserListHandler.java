package cn.itcast.hiss.process.activiti.handler.sys.user;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.PageResponseResult;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.SystemUserMessage;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import cn.itcast.hiss.message.sys.pojo.UserApp;
import cn.itcast.hiss.process.activiti.mapper.HissSystemUserMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author: miukoo
 * @describe: 用户列表查询处理器
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_SystemUserListHandler implements CmdHandler<SystemUserMessage> {

    @Autowired
    private HissSystemUserMapper hissSystemUserMapper;
    @Autowired
    private HissUserAppMapper hissUserAppMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        SystemUser systemUser = (SystemUser) params.getPalyload();
        LambdaQueryWrapper<HissSystemUser> wrapper = Wrappers.lambdaQuery();
        MessageAuth messageAuth = params.getMessageAuth();
        CurrentUser currentUser = messageAuth.getCurrentUser();
        wrapper.eq(!isAdmin(params),HissSystemUser::getId,currentUser.getUserId());
        wrapper.like(StrUtil.isNotEmpty(systemUser.getUsername()),HissSystemUser::getUsername,systemUser.getUsername());
        wrapper.like(StrUtil.isNotEmpty(systemUser.getName()),HissSystemUser::getName,systemUser.getName());
        wrapper.like(systemUser.getStatus()!=null,HissSystemUser::getStatus,systemUser.getStatus());
        if(systemUser.getCreatedTime()!=null&&systemUser.getCreatedTime().size()==2){
            wrapper.between(HissSystemUser::getCreatedTime,systemUser.getCreatedTime().get(0),systemUser.getCreatedTime().get(1));
        }
        systemUser.checkParam();
        Page<HissSystemUser> page = Page.of(systemUser.getCurrent(),systemUser.getPageSize());
        page = hissSystemUserMapper.selectPage(page, wrapper);
        PageResponseResult pageResponseResult = new PageResponseResult(page.getCurrent(),page.getSize(),page.getTotal());
        List<SystemUser> collect = page.getRecords().stream().map(item -> {
            SystemUser user = new SystemUser();
            BeanUtils.copyProperties(item, user);
            user.setPassword(null);
            user.setCTime(item.getCreatedTime());
            if (user.getId().equalsIgnoreCase(HissProcessConstants.ADMIN_ID)) {
                user.setAppName("*");
            } else {
                user.setAppName(hissUserAppMapper.getUserAppNames(user.getId()));
            }
            return user;
        }).collect(Collectors.toList());
        pageResponseResult.setData(collect);
        messageContext.addResultAndCount("result", pageResponseResult);
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_SYSTEM_USER_LIST.getId();
    }
}
