package cn.itcast.hiss.client.handler.sys.methods;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.service.variables.SysDirectUserService;
import cn.itcast.hiss.common.enums.InputType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * SysDirectUserMethod
 *
 * @author: wgl
 * @describe: 注意：指定用户审批是用户必须要实现的service，否则会报错 无法启动
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SysDirectUserMethod extends MethodType {

    private SysDirectUserService sysDirectLeaderService;

    @PostConstruct
    public void init() throws Exception {
        log.info("系统必要的组件指定用户初始化");
        try {
            SysDirectUserService bean = SpringUtil.getBean(SysDirectUserService.class);
            if (ObjectUtil.isNotNull(bean)) {
                sysDirectLeaderService = bean;
                setImpl(true);
            } else {
                log.info("hiss系统缺少必要的组件(指定用户)初始化失败，请手动实现SysDirectUserService接口并注入到spring容器中");
                throw new Exception("指定用户审批是用户必须要实现的service，系统当前无法启动");
            }
        } catch (Exception e) {
            log.info("hiss系统缺少必要的组件(指定用户)初始化失败，请手动实现SysDirectUserService接口并注入到spring容器中");
            throw new Exception("指定用户审批是用户必须要实现的service，请手动实现SysDirectUserService接口并注入到spring容器中,系统当前无法启动");
        }
    }


    @Override
    public String type() {
        return HissProcessConstants.CLIENT_USER_NAME;
    }

    @Override
    public String name() {
        return "指定用户审批";
    }

    @Override
    public String description() {
        return "描述：指定用户审批";
    }

    @Override
    public InputType inputType() {
        return InputType.TREEQUERY;
    }

    @Override
    public List<VariablesResultDTO> get(VariablesGetDTO getDTO) {
        return sysDirectLeaderService.get(getDTO);
    }

    @Override
    public List<VariablesResultDTO> query(VariablesQueryDTO queryDTO) {
        return sysDirectLeaderService.query(queryDTO);
    }

    @Override
    public List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO) {
        return sysDirectLeaderService.tree(treeDTO);
    }
}
