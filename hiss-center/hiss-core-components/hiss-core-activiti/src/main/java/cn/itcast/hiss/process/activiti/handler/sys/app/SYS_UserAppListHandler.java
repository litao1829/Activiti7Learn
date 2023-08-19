package cn.itcast.hiss.process.activiti.handler.sys.app;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.PageResponseResult;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.UserAppMessage;
import cn.itcast.hiss.message.sys.pojo.UserApp;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * SYS_UserAppListHandler
 *
 * @author: miukoo
 * @describe: 用户应用列表查询处理器
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_UserAppListHandler implements CmdHandler<UserAppMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        UserApp userApp = (UserApp) params.getPalyload();
        LambdaQueryWrapper<HissUserApp> wrapper = Wrappers.lambdaQuery();
        MessageAuth messageAuth = params.getMessageAuth();
        CurrentUser currentUser = messageAuth.getCurrentUser();
        wrapper.eq(!isAdmin(params),HissUserApp::getUserId,currentUser.getUserId());
        wrapper.like(StrUtil.isNotEmpty(userApp.getAppName()),HissUserApp::getAppName,userApp.getAppName());
        wrapper.like(StrUtil.isNotEmpty(userApp.getAppId()),HissUserApp::getAppId,userApp.getAppId());
        wrapper.like(userApp.getStatus()!=null,HissUserApp::getStatus,userApp.getStatus());
        if(userApp.getUpdatedTime()!=null&&userApp.getUpdatedTime().size()==2){
            wrapper.between(HissUserApp::getUpdatedTime,userApp.getUpdatedTime().get(0),userApp.getUpdatedTime().get(1));
        }
        if(userApp.getCreatedTime()!=null&&userApp.getCreatedTime().size()==2){
            wrapper.between(HissUserApp::getCreatedTime,userApp.getCreatedTime().get(0),userApp.getCreatedTime().get(1));
        }
        userApp.checkParam();
        Page<HissUserApp> page = Page.of(userApp.getCurrent(),userApp.getPageSize());
        page = hissUserAppMapper.selectPage(page, wrapper);
        PageResponseResult pageResponseResult = new PageResponseResult(page.getCurrent(),page.getSize(),page.getTotal());
        pageResponseResult.setData(page.getRecords());
        messageContext.addResultAndCount("result", pageResponseResult);
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_USER_APP_LIST.getId();
    }
}
