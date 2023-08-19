package cn.itcast.hiss.client.handler.custom;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.client.manager.HissVariableHandlerManager;
import cn.itcast.hiss.client.variables.VariablesHandler;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.UserInfoMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CustomVariablesHandler
 *
 * @author: wgl
 * @describe: 用户自定义变量handler
 * @date: 2022/12/28 10:10
 */
@Component
public class CustomVariablesHandler implements CmdHandler<UserInfoMessage> {



    @Override
    public void invoke(Message params, MessageContext messageContext) {
        UserInfo palyload = (UserInfo) params.getPalyload();
        String currentNodeVariable = palyload.getCurrentNodeVariable();
        VariablesHandler spelHandler = HissVariableHandlerManager.getVariableHandler(currentNodeVariable);
        if (ObjectUtil.isNotNull(spelHandler)) {
            List<String> list = spelHandler.invoke(palyload);
            messageContext.setResult(new ConcurrentHashMap<>() {
                {
                    put("list", list);
                }
            });
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.CUSTOM_VARIABLES.getId();
    }
}