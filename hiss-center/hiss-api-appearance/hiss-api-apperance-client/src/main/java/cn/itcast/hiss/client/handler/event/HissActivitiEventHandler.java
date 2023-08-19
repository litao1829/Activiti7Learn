package cn.itcast.hiss.client.handler.event;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.mess.HissActivitiEventMessage;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.handler.HandlerIdServerEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/*
 * @author miukoo
 * @description 服务器发送给客户端的事件执行器
 * @date 2023/5/26 15:00
 * @version 1.0
 **/
@Component
@Slf4j
public class HissActivitiEventHandler implements CmdHandler<HissActivitiEventMessage>, ApplicationContextAware {

    private final static Map<EventOperationTypeEnum, HissEventExecuter> MAPS = new HashMap<>();

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        HissActivitiEvent palyload = (HissActivitiEvent) params.getPalyload();
        HissEventExecuter hissEventExecuter = MAPS.get(palyload.getOperationType());
        if (hissEventExecuter != null) {
            hissEventExecuter.executer(palyload, params, messageContext);
        } else {
            log.warn("未找到对应的事件执行器：" + palyload.getOperationType());
        }
    }

    @Override
    public String getId() {
        return HandlerIdServerEnum.EVENT_ACTIVITI_PROCESS_NOTICE.getId();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(HissEventExecuter.class);
        for (String name : beanNamesForType) {
            HissEventExecuter bean = applicationContext.getBean(name, HissEventExecuter.class);
            MAPS.put(bean.getType(), bean);
        }
    }
}
