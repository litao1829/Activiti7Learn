package cn.itcast.hiss.process.activiti.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.process.activiti.service.ActivitiService;
import cn.itcast.hiss.process.activiti.service.UserTaskService;
import cn.itcast.hiss.process.activiti.util.VariableUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * @author miukoo
 * @description 流程办理各功能实现
 * @date 2023/6/3 11:19
 * @version 1.0
 **/
@Slf4j
@Service
@Transactional
public class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    private UserTaskService userTaskService;

    @Autowired
    private HissServerApperanceTemplate hissServerApperanceTemplate;

    @Override
    public Set<String> doGetClientUserInfo(DelegateExecution execution, String assignee) {
        Set<String> resClientNames = new HashSet<>();
        Map<String, Object> variables = execution.getVariables();
        //判断是否是客户端变量--如果是客户端变量，那么就提取客户端请求信息
        if (VariableUtil.checkIsClientVariable(assignee)) {
            String[] varList = VariableUtil.getVariables(assignee);
            Object variableValue = variables.get(varList[0]);
            if (ObjectUtil.isNotNull(variableValue)) {
                //说明是流程变量
                if (variableValue.toString().startsWith(SystemConstant.CLIENT_FLAG)) {
                    //TODO 向客户端请求数据--需要获取流程发起人的信息
                    UserInfo userInfo = userTaskService.getBeforeNodeUserInfo(execution);
                    String[] split = variableValue.toString().split("/");
                    userInfo.setCurrentNodeVariable(split[1]);
                    userInfo.setCurrentNodeValue(varList[1]);
                    log.info("当前节点需要的用户信息:{},需要往客户端请求数据", assignee);
                    List<String> name = hissServerApperanceTemplate.getUserInfo(execution.getTenantId(), userInfo);
                    resClientNames.addAll(name);
                } else {
                    //说明是固定的值--不需要请求客户端
                    resClientNames.add(variableValue.toString());
                }
            } else {
                //虽然是变量 但客户端并未上报
                resClientNames.add(assignee);
            }
        } else {
            //不需要请求客户端直接添加
            resClientNames.add(assignee);
        }
        return resClientNames;
    }


}
