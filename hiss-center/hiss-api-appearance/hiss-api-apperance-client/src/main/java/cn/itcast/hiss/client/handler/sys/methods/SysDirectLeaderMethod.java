package cn.itcast.hiss.client.handler.sys.methods;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.service.variables.SysDirectLeaderService;
import cn.itcast.hiss.client.service.variables.SysDirectUserService;
import cn.itcast.hiss.common.enums.InputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDirectLeaderMethod
 *
 * @author: wgl
 * @describe: 直属领导审批
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SysDirectLeaderMethod extends MethodType {

    @Lazy
    @Autowired(required = false)
    private SysDirectLeaderService sysDirectLeaderService;

    @Override
    public String type() {
        return "client.leader";
    }

    @Override
    public String name() {
        return "直属领导审批";
    }

    @Override
    public String description() {
        return "描述";
    }

    @Override
    public InputType inputType() {
        return InputType.NONE;
    }

    @PostConstruct
    public void init() throws Exception {
        try{
            SysDirectLeaderService bean = SpringUtil.getBean(SysDirectLeaderService.class);
            if (ObjectUtil.isNotNull(bean)) {
                sysDirectLeaderService = bean;
                setImpl(true);
            }
        } catch (Exception e){
            log.warn("未初始化{}变量的配置实现",getName());
        }
    }

    @Override
    public List<VariablesResultDTO> get(VariablesGetDTO getDTO) {
        return ObjectUtil.isNull(sysDirectLeaderService)?null:sysDirectLeaderService.get(getDTO);
    }

    @Override
    public List<VariablesResultDTO> query(VariablesQueryDTO queryDTO) {
        return ObjectUtil.isNull(sysDirectLeaderService)?null:sysDirectLeaderService.query(queryDTO);
    }

    @Override
    public List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO) {
        return ObjectUtil.isNull(sysDirectLeaderService)?null:sysDirectLeaderService.tree(treeDTO);
    }
}
