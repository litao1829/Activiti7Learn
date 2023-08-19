package cn.itcast.hiss.client.handler.sys.methods;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.service.variables.SysDesignateDepartmentService;
import cn.itcast.hiss.client.service.variables.SysDirectLeaderService;
import cn.itcast.hiss.common.enums.InputType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * SysCustomVariableMethod
 *
 * @author: miukoo
 * @describe: 自定义变量
 * @date: 2022/12/28 10:10
 */
@Component
public class SysCustomVariableMethod extends MethodType {

    @Override
    public String type() {
        return "client.custom.variable";
    }

    @Override
    public String name() {
        return "自定义变量";
    }

    @Override
    public String description() {
        return "描述";
    }

    @PostConstruct
    public void init() throws Exception {
        setImpl(true);
    }

    @Override
    public InputType inputType() {
        return InputType.INPUT;
    }

    @Override
    public List<VariablesResultDTO> get(VariablesGetDTO getDTO) {
        return new ArrayList<>();
    }

    @Override
    public List<VariablesResultDTO> query(VariablesQueryDTO queryDTO) {
        return new ArrayList<>();
    }

    @Override
    public List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO) {
        return new ArrayList<>();
    }
}
