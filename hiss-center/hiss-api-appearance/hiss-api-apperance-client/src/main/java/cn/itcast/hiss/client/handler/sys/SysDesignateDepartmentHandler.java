package cn.itcast.hiss.client.handler.sys;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.handler.sys.methods.SysDesignateDepartmentMethod;
import cn.itcast.hiss.client.service.UserInfoService;
import cn.itcast.hiss.client.variables.VariablesHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * AccountingHandler
 *
 * @author: wgl
 * @describe: 指定部门
 * @date: 2022/12/28 10:10
 */
@HissVariables(key="client.dept",value = "hiss_client_dept", description = "指定部门",methodType = SysDesignateDepartmentMethod.class)
@Component
@Slf4j
public class SysDesignateDepartmentHandler implements VariablesHandler {

    @Autowired(required = false)
    private UserInfoService userInfoService;

    @Override
    public List<String> invoke(UserInfo userInfo) {
        log.info("收到来自部门领导审批的消息为：{}", userInfo);

        List<String> names = new ArrayList<>();
        if(ObjectUtil.isNotNull(userInfoService)){
            names = userInfoService.getDesignateDepartment(userInfo);
        }
        return names;
    }
}