package cn.itcast.hiss.process.activiti.handler.sys;

import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.VariableMessage;
import cn.itcast.hiss.process.activiti.variables.SysVariableManager;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

;

/**
 * SYS_UELVariableHandler
 *
 * @author: wgl
 * @describe: 系统的UEL表达式变量处理器
 * @date: 2022/12/28 10:10
 */
@Component
public class SYS_GetVariableHandler implements CmdHandler {

    @Autowired
    private TaskService taskService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        messageContext.addResultAndCount("result",SysVariableManager.getTenantVariable(params.getMessageAuth().getTenant()));
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_GET_VARIABLE.getId();
    }
}
