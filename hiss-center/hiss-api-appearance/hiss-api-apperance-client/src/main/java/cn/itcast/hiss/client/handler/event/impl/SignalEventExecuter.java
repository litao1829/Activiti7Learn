package cn.itcast.hiss.client.handler.event.impl;

import cn.itcast.hiss.client.handler.event.extension.ActivitiSignalListener;
import cn.itcast.hiss.client.handler.event.HissEventExecuter;
import cn.itcast.hiss.common.dtos.Complex;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/*
 * @author miukoo
 * @description 回调信号
 * @date 2023/5/26 15:20
 * @version 1.0
 **/
@Component
@Slf4j
public class SignalEventExecuter implements HissEventExecuter, ApplicationContextAware {
    @Autowired
    ApplicationContext applicationContext;

    static ConcurrentHashMap<String, List<Complex<String, ActivitiSignalListener>>> SIGNAL_LISTENER = new ConcurrentHashMap<>();

    @Override
    public EventOperationTypeEnum getType() {
        return EventOperationTypeEnum.SIGNAL_EVENT_NOTICE;
    }

    @Override
    public void executer(HissActivitiEvent hissActivitiEvent, Message message, MessageContext messageContext) {
        String signalName = hissActivitiEvent.getTargetName();
        List<Complex<String, ActivitiSignalListener>> list = SIGNAL_LISTENER.get(signalName);
        if(list!=null){
            for (Complex<String, ActivitiSignalListener> complex : list) {
                try{
                    complex.getSecond().onSignal(hissActivitiEvent);
                }catch (Exception e){
                    e.printStackTrace();
                    messageContext.addError(EventOperationTypeEnum.SIGNAL_EVENT_NOTICE.name(),"执行SIGNAL【"+signalName+"】 时出错："+e.getMessage());
                    log.error("执行SIGNAL【{}】 时出错：{}",signalName,e);
                }
            }
        }
    }

    /**
     * 扫描所有的信号监听器
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ActivitiSignalListener> beans = applicationContext.getBeansOfType(ActivitiSignalListener.class);
        Map<String, List<Complex<String, ActivitiSignalListener>>> collect = beans.values().stream().flatMap(lis -> {
            List<Complex<String, ActivitiSignalListener>> temp = new ArrayList<>();
            for (String name : lis.signalName()) {
                temp.add(new Complex<>(name, lis));
            }
            return temp.stream();
        }).collect(Collectors.groupingBy(Complex::getFirst));
        SIGNAL_LISTENER.putAll(collect);
    }
}
