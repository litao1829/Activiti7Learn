package cn.itcast.hiss.client.handler.event.impl;

import cn.itcast.hiss.client.handler.HissAnnotationMethodManager;
import cn.itcast.hiss.client.handler.event.HissEventExecuter;
import cn.itcast.hiss.client.handler.event.annotation.*;
import cn.itcast.hiss.client.handler.event.extension.ActivitiEventListener;
import cn.itcast.hiss.common.dtos.Complex;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.common.enums.HissActivitiEventTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
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
@DependsOn("hissAnnotationMethodManager")
@Slf4j
public class GlobalEventExecuter implements HissEventExecuter, ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    ApplicationContext applicationContext;

    static ConcurrentHashMap<HissActivitiEventTypeEnum, List<Complex<HissActivitiEventTypeEnum, ActivitiEventListener>>> GLOBAL_EVENT_LISTENER = new ConcurrentHashMap<>();

    @Override
    public EventOperationTypeEnum getType() {
        return EventOperationTypeEnum.GLOBAL_EVENT_NOTICE;
    }

    @Override
    public void executer(HissActivitiEvent hissActivitiEvent, Message message, MessageContext messageContext) {
        String eventName = hissActivitiEvent.getEventName();
        List<Complex<HissActivitiEventTypeEnum, ActivitiEventListener>> list = GLOBAL_EVENT_LISTENER.get(HissActivitiEventTypeEnum.valueOf(eventName));
        if (list != null) {
            for (Complex<HissActivitiEventTypeEnum, ActivitiEventListener> complex : list) {
                try {
                    complex.getSecond().onEvent(hissActivitiEvent);
                    messageContext.addResultAndCount("msg","通知成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    messageContext.addError(EventOperationTypeEnum.GLOBAL_EVENT_NOTICE.name(), "执行GlobalEvent【" + eventName + "】 时出错：" + e.getMessage());
                    log.error("执行GlobalEvent【{}】 时出错：{}", eventName, e);
                }
            }
        }
    }

    /**
     * 扫描所有的信号监听器
     *
     * @throws BeansException
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, ActivitiEventListener> beans = applicationContext.getBeansOfType(ActivitiEventListener.class);
        Map<HissActivitiEventTypeEnum, List<Complex<HissActivitiEventTypeEnum, ActivitiEventListener>>> collect = beans.values().stream().flatMap(lis -> {
            List<Complex<HissActivitiEventTypeEnum, ActivitiEventListener>> temp = new ArrayList<>();
            for (HissActivitiEventTypeEnum name : lis.eventType()) {
                temp.add(new Complex<>(name, lis));
            }
            return temp.stream();
        }).collect(Collectors.groupingBy(Complex::getFirst));
        GLOBAL_EVENT_LISTENER.putAll(collect);
        scanAnnotationMethod(HissActivitiEventTypeEnum.PROCESS_STARTED, ActivitiProcessStart.class);//支持方法注解方式监听流程启动
        scanAnnotationMethod(HissActivitiEventTypeEnum.PROCESS_COMPLETED, ActivitiProcessEnd.class);//支持方法注解方式监听流程完成
        scanAnnotationMethod(HissActivitiEventTypeEnum.TASK_CREATED, ActivitiTaskCreated.class);//支持方法注解方式监听任务新办理消息
        scanAnnotationMethod(HissActivitiEventTypeEnum.TASK_COMPLETED, ActivitiTaskCompleted.class);//支持方法注解方式监听任务办完消息
        scanAnnotationMethod(HissActivitiEventTypeEnum.USER_EXPEDITE, ActivitiTaskExpedite.class);//支持方法注解方式监听任务催办消息

    }

    private void scanAnnotationMethod(HissActivitiEventTypeEnum hissActivitiEventTypeEnum, Class aClass) {
        List<Complex<Method, Object>> temp = HissAnnotationMethodManager.getAnnotationMethods(aClass);
        List<Complex<HissActivitiEventTypeEnum, ActivitiEventListener>> list = GLOBAL_EVENT_LISTENER.getOrDefault(hissActivitiEventTypeEnum, new ArrayList<>());
        for (Complex<Method, Object> complex : temp) {
            ActivitiEventListener proxy = HissAnnotationMethodManager.createProxy(complex.getSecond(), complex.getFirst(), ActivitiEventListener.class);
            list.add(new Complex<>(hissActivitiEventTypeEnum, proxy));
            // 自从测试一下。
//            proxy.onEvent(new HissActivitiEvent());
        }
        GLOBAL_EVENT_LISTENER.put(hissActivitiEventTypeEnum, list);
    }
}
