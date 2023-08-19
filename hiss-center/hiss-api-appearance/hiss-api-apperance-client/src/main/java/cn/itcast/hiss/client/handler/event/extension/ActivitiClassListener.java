package cn.itcast.hiss.client.handler.event.extension;

import cn.itcast.hiss.event.pojo.HissActivitiEvent;

import java.util.Map;

/*
 * @author miukoo
 * @description 执行JavaClass/Bean的接口
 * @date 2023/5/26 15:38
 * @version 1.0
 **/
public interface ActivitiClassListener {

    /**
     * 返回的map将作为全局变量
     * @param hissActivitiEvent  参数
     * @return
     */
    Map<String,String> onEvent(HissActivitiEvent hissActivitiEvent);

}
