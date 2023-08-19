package cn.itcast.hiss.event.mess;

import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.handler.HandlerIdServerEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/*
 * @author miukoo
 * @description 流程消息，用于通知到客户端
 * @date 2023/5/26 13:19
 * @version 1.0
 **/
@Data
public class HissActivitiEventMessage implements Message<HissActivitiEvent> {
    public final static String ID = HandlerIdServerEnum.EVENT_ACTIVITI_PROCESS_NOTICE.getId();
    private String id = ID;
    private MessageAuth messageAuth;
    private HissActivitiEvent palyload;
    private MessageConfig messageConfig;
}
