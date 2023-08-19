package cn.itcast.hiss.client.submit.items;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.manager.HissSubmitHandlerManager;
import cn.itcast.hiss.client.manager.HissVariableHandlerManager;
import cn.itcast.hiss.client.template.HissClientApperanceTemplate;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.submit.SubmitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * VaribaleMessage
 *
 * @author: wgl
 * @describe: 系统表达式变量
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class VaribaleMessage implements SubmitTemplate {

    @Autowired
    private HissClientApperanceTemplate hissClientApperanceTemplate;
    @Autowired
    private MessageTcpSenderProperties messageTcpSenderProperties;

    @Override
    public boolean submit() {
        try {
            Set<HissVariable> hissVariableSet = HissVariableHandlerManager.getHissVariableSet();
            List<HissVariables> uelList = HissVariableHandlerManager.getUelList();
            //将所有的Spel表达式发送到服务端
            log.info("将所有的表达式发送到服务端,内容有:{}", uelList.stream().map(HissVariables::description).collect(Collectors.toList()));
            for (HissVariables uel : uelList) {
                HissVariable hissVariable = new HissVariable(uel.key(), uel.value(), uel.description(), uel.maxSize(), CollectionUtil.newArrayList(uel.assignType()));
                Class aClass = uel.methodType();
                Map<String, Object> beansOfType = SpringUtil.getBeansOfType(aClass);
                if (ObjectUtil.isNotNull(beansOfType) && beansOfType.size() > 0) {
                    Map.Entry firstEntry = beansOfType.entrySet().iterator().next();
                    Object value = firstEntry.getValue();
                    hissVariable.setMethodType((MethodType) value);
                }
                hissVariableSet.add(hissVariable);
            }
            if (hissVariableSet.size() > 0) {
                List<HissVariable> hissVariablesList = new ArrayList<>();
                hissVariablesList.addAll(hissVariableSet);
                HissVariableHandlerManager.setHissVariableSet(hissVariableSet);
                for (String tenantId : messageTcpSenderProperties.getSources().keySet()) {
                    hissClientApperanceTemplate.sendVariable(tenantId, hissVariablesList);
                }
                return true;
            } else {
                log.info("没有需要同步的表达式，不再同步");
                //移除当前任务
                HissSubmitHandlerManager.removeSubmitTemplate(this);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
