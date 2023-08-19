package cn.itcast.hiss.client.handler.event.impl;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.client.exception.BpmnException;
import cn.itcast.hiss.client.handler.event.HissEventExecuter;
import cn.itcast.hiss.client.handler.event.extension.ActivitiClassListener;
import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
 * @author miukoo
 * @description 执行Java类
 * @date 2023/5/26 15:20
 * @version 1.0
 **/
@Component
@Slf4j
public class JavaClassEventExecuter implements HissEventExecuter {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public EventOperationTypeEnum getType() {
        return EventOperationTypeEnum.EXECUTE_CLASS;
    }

    @Override
    public void executer(HissActivitiEvent hissActivitiEvent, Message message, MessageContext messageContext) {
        // className 如果是#开头，则为bean
        String className = hissActivitiEvent.getTargetName();
        try {
            Class<?> aClass = null;
            Object instance = null;
            if(className.startsWith("#")){
                instance = applicationContext.getBean(className.substring(1));
                if(instance!=null){
                    aClass = instance.getClass();
                }
            }else{
                aClass = Class.forName(className);
                instance = aClass.getDeclaredConstructor().newInstance();
            }
            if(aClass!=null&&instance!=null){
               if(instance instanceof ActivitiClassListener){

                   ActivitiClassListener javaClassListenner = (ActivitiClassListener)instance;
                   Map<String, String> variable = javaClassListenner.onEvent(hissActivitiEvent);
                   messageContext.addResult(EventOperationTypeEnum.EXECUTE_CLASS.name(),variable);

               }else{
                   messageContext.addError(EventOperationTypeEnum.EXECUTE_CLASS.name(),"对应的"+className+"类没有实现JavaClassListenner接口");
                   log.error("对应的{}类没有实现JavaClassListenner接口",className);
               }
            }else{
                messageContext.addError(EventOperationTypeEnum.EXECUTE_CLASS.name(),"未来找到对应的执行类："+className);
                log.error("未来找到对应的执行类：{}",className);
            }
        } catch (BpmnException e) {
            e.printStackTrace();
            messageContext.addError(HissProcessConstants.BPMN_ERROR,e.getErrorCode());
            messageContext.addError(EventOperationTypeEnum.EXECUTE_CLASS.name(),"执行Java Class/Bean【"+className+"】 时出错："+e.getMessage());
            log.error("抛出bpmn错误代码：{}",className);
        }  catch (ClassNotFoundException e) {
            e.printStackTrace();
            messageContext.addError(EventOperationTypeEnum.EXECUTE_CLASS.name(),"未来找到对应的执行类："+className);
            log.error("未来找到对应的执行类：{}",className);
        } catch (Exception e) {
            e.printStackTrace();
            messageContext.addError(EventOperationTypeEnum.EXECUTE_CLASS.name(),"执行Java Class/Bean【"+className+"】 时出错："+e.getMessage());
            log.error("执行Java Class/Bean【{}】 时出错：{}",className,e);
        }
    }

}
