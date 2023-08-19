package cn.itcast.hiss.client.handler.event.extension;

import cn.itcast.hiss.event.pojo.HissActivitiEvent;

import java.util.List;

/*
 * @author miukoo
 * @description 监听流程抛出的错误码
 * @date 2023/5/26 15:38
 * @version 1.0
 **/
public interface ActivitiErrorCodeListener {

    /**
     * 监听的错误码
     * @return
     */
    List<String> errorCode();

    /**
     * 触发的消息
     * @param hissActivitiEvent  参数
     * @return
     */
    void onErrorCode(HissActivitiEvent hissActivitiEvent);

}
