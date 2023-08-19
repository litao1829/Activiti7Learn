package cn.itcast.hiss.client.handler.event.extension;

import cn.itcast.hiss.common.enums.HissActivitiEventTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;

import java.util.List;

/*
 * @author miukoo
 * @description 监听流程各种事件
 * @date 2023/5/26 15:38
 * @version 1.0
 **/
public interface ActivitiEventListener {

    /**
     * 监听的错误码
     * @return
     */
    List<HissActivitiEventTypeEnum> eventType();

    /**
     * 触发事件
     * @param hissActivitiEvent  参数
     * @return
     */
    void onEvent(HissActivitiEvent hissActivitiEvent);

}
