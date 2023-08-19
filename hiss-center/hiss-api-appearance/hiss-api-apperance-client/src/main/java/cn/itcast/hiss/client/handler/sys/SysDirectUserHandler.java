package cn.itcast.hiss.client.handler.sys;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.handler.sys.methods.SysDirectLeaderMethod;
import cn.itcast.hiss.client.handler.sys.methods.SysDirectUserMethod;
import cn.itcast.hiss.client.service.UserInfoService;
import cn.itcast.hiss.client.variables.VariablesHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * yrdyHandler
 *
 * @author: wgl
 * @describe: 指定用户handler
 * @date: 2022/12/28 10:10
 */
@HissVariables(key="client.directUser",value = "hiss_client_user", description = "指定用户",methodType = SysDirectUserMethod.class)
@Component
@Slf4j
public class SysDirectUserHandler implements VariablesHandler {

    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Override
    public List<String> invoke(UserInfo userInfo) {
        List<String> names = new ArrayList<>();
        if(ObjectUtil.isNotNull(userInfoService)){
            names = userInfoService.getDirectUser(userInfo);
        }
        return names;
    }
}
