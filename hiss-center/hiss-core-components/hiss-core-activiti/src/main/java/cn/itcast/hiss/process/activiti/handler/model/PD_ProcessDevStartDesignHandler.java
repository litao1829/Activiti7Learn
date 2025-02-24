package cn.itcast.hiss.process.activiti.handler.model;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.processdefinition.ProcessDesignModelMessage;
import cn.itcast.hiss.process.activiti.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 启动开发流程设计
 * @date 2023/5/25 16:26
 * @version 1.0
 **/
@Component
public class PD_ProcessDevStartDesignHandler implements CmdHandler<ProcessDesignModelMessage> {

    @Autowired
    ActivitiService activitiService;

    @Override
    public void invoke(Message message, MessageContext messageContext) {
        activitiService.startByModel(message, messageContext,true);
    }


    @Override
    public String getId() {
        return HandlerIdClientEnum.PD_FLOW_M_START_MODEL_FOR_DEV.getId();
    }
}
