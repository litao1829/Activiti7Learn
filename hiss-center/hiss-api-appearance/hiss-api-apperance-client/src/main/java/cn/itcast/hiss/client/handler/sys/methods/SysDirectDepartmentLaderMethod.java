package cn.itcast.hiss.client.handler.sys.methods;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.service.variables.SysDirectDepartmentLaderService;
import cn.itcast.hiss.client.service.variables.SysDirectLeaderService;
import cn.itcast.hiss.common.enums.InputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDirectDepartmentLaderMethod
 *
 * @author: wgl
 * @describe: 直属部门领导
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SysDirectDepartmentLaderMethod extends MethodType {

    @Autowired(required = false)
    @Lazy
    private SysDirectDepartmentLaderService sysDirectDepartmentLaderService;

    @PostConstruct
    public void init() throws Exception {
        try{
            SysDirectDepartmentLaderService bean = SpringUtil.getBean(SysDirectDepartmentLaderService.class);
            if (ObjectUtil.isNotNull(bean)) {
                sysDirectDepartmentLaderService = bean;
                setImpl(true);
            }
        } catch (Exception e){
            log.warn("未初始化{}变量的配置实现",getName());
        }
    }

    @Override
    public String type() {
        return "client.departmentLeader";
    }

    @Override
    public String name() {
        return "直属部门领导";
    }

    @Override
    public String description() {
        return "系统内置的直属部门领导";
    }

    @Override
    public InputType inputType() {
        return InputType.NONE;
    }

    @Override
    public List<VariablesResultDTO> get(VariablesGetDTO getDTO) {
        return ObjectUtil.isNull(sysDirectDepartmentLaderService)?null:sysDirectDepartmentLaderService.get(getDTO);
    }

    @Override
    public List<VariablesResultDTO> query(VariablesQueryDTO queryDTO) {
        return ObjectUtil.isNull(sysDirectDepartmentLaderService)?null:sysDirectDepartmentLaderService.query(queryDTO);
    }

    @Override
    public List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO) {
        return ObjectUtil.isNull(sysDirectDepartmentLaderService)?null:sysDirectDepartmentLaderService.tree(treeDTO);
    }
}
