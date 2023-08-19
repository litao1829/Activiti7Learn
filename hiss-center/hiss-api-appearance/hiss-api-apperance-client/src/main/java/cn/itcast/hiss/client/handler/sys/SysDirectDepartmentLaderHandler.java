package cn.itcast.hiss.client.handler.sys;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.handler.sys.methods.SysDirectDepartmentLaderMethod;
import cn.itcast.hiss.client.service.UserInfoService;
import cn.itcast.hiss.client.variables.VariablesHandler;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdServerEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.UserInfoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * yrdyHandler
 *
 * @author: wgl
 * @describe: 直属部门领导handler
 * @date: 2022/12/28 10:10
 */
@HissVariables(key="client.departmentLeader",value = "hiss_client_departmentLeader", description = "直属部门领导", methodType = SysDirectDepartmentLaderMethod.class)
@Component
@Slf4j
public class SysDirectDepartmentLaderHandler implements VariablesHandler {

    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Override
    public List<String> invoke(UserInfo userInfo) {
        List<String> names = new ArrayList<>();
        if(ObjectUtil.isNotNull(userInfoService)){
            names = userInfoService.getDirectDepartmentLeader(userInfo);
        }
        return names;
    }
}
