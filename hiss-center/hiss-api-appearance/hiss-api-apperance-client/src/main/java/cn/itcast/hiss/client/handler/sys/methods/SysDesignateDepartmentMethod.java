package cn.itcast.hiss.client.handler.sys.methods;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.service.variables.SysDesignateDepartmentService;
import cn.itcast.hiss.client.service.variables.SysDesignateRoleService;
import cn.itcast.hiss.common.enums.InputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDesignateDepartmentMethod
 *
 * @author: wgl
 * @describe: 指定部门
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SysDesignateDepartmentMethod extends MethodType {

    @Autowired(required = false)
    @Lazy
    private SysDesignateDepartmentService sysDesignateDepartmentService;

    @PostConstruct
    public void init() throws Exception {
        try {
            SysDesignateDepartmentService bean = SpringUtil.getBean(SysDesignateDepartmentService.class);
            if (ObjectUtil.isNotNull(bean)) {
                sysDesignateDepartmentService = bean;
                setImpl(true);
            }
        } catch (Exception e){
            log.warn("未初始化{}变量的配置实现",getName());
        }
    }

    @Override
    public String type() {
        return "client.dept";
    }

    @Override
    public String name() {
        return "指定部门";
    }

    @Override
    public String description() {
        return "系统内置的指定部门";
    }

    @Override
    public InputType inputType() {
        return InputType.TREEQUERY;
    }

    @Override
    public List<VariablesResultDTO> get(VariablesGetDTO getDTO) {
        //获取
        return ObjectUtil.isNull(sysDesignateDepartmentService)?null:sysDesignateDepartmentService.get(getDTO);
    }

    @Override
    public List<VariablesResultDTO> query(VariablesQueryDTO queryDTO) {
        return ObjectUtil.isNull(sysDesignateDepartmentService)?null:sysDesignateDepartmentService.query(queryDTO);
    }

    @Override
    public List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO) {
        return ObjectUtil.isNull(sysDesignateDepartmentService)?null:sysDesignateDepartmentService.tree(treeDTO);
    }
}
