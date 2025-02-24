package cn.itcast.hiss.process.activiti.handler.model;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.processdefinition.ProcessDesignModelMessage;
import cn.itcast.hiss.process.activiti.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

;

/**
 * @author: miukoo
 * @describe: 把设计部署
 * @date: 2022/12/28 10:10
 */
@Component
public class PD_ModelDeplomentHandler implements CmdHandler<ProcessDesignModelMessage> {

    @Autowired
    ActivitiService activitiService;

    /**
     *  部署
     *
     * @param messageContext
     */
    @Override
    public void invoke(Message message, MessageContext messageContext) {
        activitiService.modelToDeploment(message, messageContext);
    }

    /**
     * 命令ID
     *
     * @return
     */
    @Override
    public String getId() {
        return HandlerIdClientEnum.PD_MODEL_TO_DEPLOYMENT.getId();
    }
}
