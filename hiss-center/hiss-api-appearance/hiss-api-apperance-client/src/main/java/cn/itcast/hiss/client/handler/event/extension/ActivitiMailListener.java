package cn.itcast.hiss.client.handler.event.extension;

import cn.itcast.hiss.event.pojo.HissActivitiEvent;

/*
 * @author miukoo
 * @description 监听发送邮件的事件
 * @date 2023/5/26 15:38
 * @version 1.0
 **/
public interface ActivitiMailListener {


    /**
     * 发送邮件任务事件
     * @param hissActivitiEvent  参数-邮件参数信息参加eventdata
     * @return
     */
    void onMail(HissActivitiEvent hissActivitiEvent);

}
