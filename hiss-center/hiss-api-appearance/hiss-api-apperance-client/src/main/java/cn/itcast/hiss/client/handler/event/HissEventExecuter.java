package cn.itcast.hiss.client.handler.event;

import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;

/*
 * @author miukoo
 * @description 服务器事件执行器
 * @date 2023/5/26 15:11
 * @version 1.0
 **/
public interface HissEventExecuter {

    public EventOperationTypeEnum getType();

    void executer(HissActivitiEvent hissActivitiEvent, Message message, MessageContext messageContext);
}
