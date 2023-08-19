package cn.itcast.hiss.process.activiti.handler.sys;

import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.VariableMethodMessage;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SYS_BisVariableFromClientHandler
 *
 * @author: wgl
 * @describe: 服务端获取客户端变量的方法
 * @date: 2022/12/28 10:10
 */
@Component
public class SYS_BisVariableFromClientHandler implements CmdHandler<VariableMethodMessage> {

    @Autowired
    private HissServerApperanceTemplate hissServerApperanceTemplate;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        VariableMethod palyload = (VariableMethod) params.getPalyload();
        //TODO 这里提交测试的时候需要重新修改 商户ID 需要从playLoad中获取
//        List<VariablesResultDTO> res = hissServerApperanceTemplate.getClientVariablesMethod(palyload.getTenantId(), palyload);
        List<Map> res = hissServerApperanceTemplate.getClientVariablesMethod(params.getMessageAuth().getTenant(), palyload);
        messageContext.addResultAndCount("result", res);
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SERVER_VARIABLES_METHOD.getId();
    }
}
