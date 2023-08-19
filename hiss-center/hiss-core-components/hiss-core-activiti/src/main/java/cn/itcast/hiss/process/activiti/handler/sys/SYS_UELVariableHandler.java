package cn.itcast.hiss.process.activiti.handler.sys;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.client.common.HissVariableServer;
import cn.itcast.hiss.api.client.common.MethodTypeServer;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.VariableMessage;
import cn.itcast.hiss.process.activiti.variables.SysVariableManager;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * SYS_UELVariableHandler
 *
 * @author: wgl
 * @describe: 系统的UEL表达式变量处理器
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_UELVariableHandler implements CmdHandler<VariableMessage> {

    @Autowired
    private TaskService taskService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        List<HissVariableServer> result = new ArrayList<>();
        List<JSONObject> hissVariableList = (List<JSONObject>) params.getPalyload();
        for (JSONObject index : hissVariableList) {
            HissVariableServer hissVariable = new HissVariableServer(index.getString("key"), index.getString("value"), index.getString("description"), index.getInteger("maxSize"), index.getObject("assignType", List.class));
            MethodTypeServer methodType = index.getObject("methodType", MethodTypeServer.class);
            if (ObjectUtil.isNotNull(methodType)) {
                hissVariable.setMethodType(methodType);
            } else {
                continue;
            }
            result.add(hissVariable);
        }
        SysVariableManager.cleanAndSetVariable(params.getMessageAuth().getTenant(), result);
        List<HissVariableServer> tenantVariable = SysVariableManager.getTenantVariable(params.getMessageAuth().getTenant());
        log.info("收到客户端同步的变量列表:{}", tenantVariable);
        messageContext.setResult(new ConcurrentHashMap<>() {
            {
                put("flag", true);
            }
        });
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_VARIABLE.getId();
    }
}