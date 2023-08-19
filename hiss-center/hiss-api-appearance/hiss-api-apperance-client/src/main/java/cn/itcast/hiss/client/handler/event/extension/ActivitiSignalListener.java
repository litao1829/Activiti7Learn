package cn.itcast.hiss.client.handler.event.extension;

import cn.itcast.hiss.event.pojo.HissActivitiEvent;

import java.util.List;

/*
 * @author miukoo
 * @description 监听流程抛出的信号
 * @date 2023/5/26 15:38
 * @version 1.0
 **/
public interface ActivitiSignalListener {

    /**
     * 监听的信号名称
     * @return
     */
    List<String> signalName();

    /**
     * 触发的信号
     * @param hissActivitiEvent  参数
     * @return
     */
    void onSignal(HissActivitiEvent hissActivitiEvent);

}
