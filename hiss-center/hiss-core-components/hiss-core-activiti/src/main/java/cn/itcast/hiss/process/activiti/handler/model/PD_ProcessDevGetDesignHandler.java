package cn.itcast.hiss.process.activiti.handler.model;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.processdefinition.ProcessDesignModelMessage;
import cn.itcast.hiss.process.activiti.service.ActivitiDesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @author miukoo
 * @description 查询开发流程设计get
 * @date 2023/5/25 16:26
 * @version 1.0
 **/
@Component
public class PD_ProcessDevGetDesignHandler implements CmdHandler<ProcessDesignModelMessage> {
    @Autowired
    ActivitiDesignerService activitiDesignerService;


    @Override
    public void invoke(Message<ProcessDesignModelMessage> message, MessageContext messageContext) {
        activitiDesignerService.get(message, messageContext);
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.PD_FLOW_M_GET_MODEL_FOR_DEV.getId();
    }
}
