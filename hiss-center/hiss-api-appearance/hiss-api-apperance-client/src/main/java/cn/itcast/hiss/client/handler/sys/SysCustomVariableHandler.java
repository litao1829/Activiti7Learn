package cn.itcast.hiss.client.handler.sys;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.handler.sys.methods.SysCustomVariableMethod;
import cn.itcast.hiss.client.handler.sys.methods.SysDirectUserMethod;
import cn.itcast.hiss.client.service.UserInfoService;
import cn.itcast.hiss.client.variables.VariablesHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SysCustomVariableHandler
 *
 * @author: miukoo
 * @describe: 自定义变量
 * @date: 2022/12/28 10:10
 */
@HissVariables(key="client.custom.variable",value = "client_custom_variable", description = "自定义变量",methodType = SysCustomVariableMethod.class)
@Component
@Slf4j
public class SysCustomVariableHandler implements VariablesHandler {


    @Override
    public List<String> invoke(UserInfo userInfo) {
        List<String> names = new ArrayList<>();
        return names;
    }
}
