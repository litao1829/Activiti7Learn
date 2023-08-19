package cn.itcast.hiss.client.handler.event.impl;

import cn.itcast.hiss.client.handler.event.HissEventExecuter;
import cn.itcast.hiss.client.handler.event.extension.ActivitiMailListener;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * @author miukoo
 * @description 回调发邮件
 * @date 2023/5/26 15:20
 * @version 1.0
 **/
@Component
@Slf4j
public class MailEventExecuter implements HissEventExecuter, ApplicationContextAware {
    @Autowired
    ApplicationContext applicationContext;

    static Set<ActivitiMailListener> MAIL_LISTENER = new HashSet<>();

    @Override
    public EventOperationTypeEnum getType() {
        return EventOperationTypeEnum.SEND_MAIL_EVENT_NOTICE;
    }

    @Override
    public void executer(HissActivitiEvent hissActivitiEvent, Message message, MessageContext messageContext) {
        String signalName = hissActivitiEvent.getTargetName();
        if(MAIL_LISTENER.size()==0){
            log.error("有任务调用了客户端的邮件发送功能，请提供相关ActivitiMailListener接口的实现~");
        }
        for (ActivitiMailListener activitiMailListener : MAIL_LISTENER) {
            try{
                activitiMailListener.onMail(hissActivitiEvent);
            }catch (Exception e){
                e.printStackTrace();
                messageContext.addError(EventOperationTypeEnum.SIGNAL_EVENT_NOTICE.name(),"执行MAIL【"+signalName+"】 时出错："+e.getMessage());
                log.error("执行MAIL【{}】 时出错：{}",signalName,e);
            }
        }
    }

    /**
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ActivitiMailListener> beans = applicationContext.getBeansOfType(ActivitiMailListener.class);
        MAIL_LISTENER.addAll(beans.values());
    }
}
