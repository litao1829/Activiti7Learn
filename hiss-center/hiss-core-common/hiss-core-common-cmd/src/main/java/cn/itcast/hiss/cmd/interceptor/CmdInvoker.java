package cn.itcast.hiss.cmd.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.cmd.ReceiverHandlerManager;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;

import javax.management.ServiceNotFoundException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * CmdInvoker
 *
 * @author: wgl
 * @describe: TODO
 * @date: 2022/12/28 10:10
 */
@Order(1000)
public class CmdInvoker extends CmdInterceptor {


    /**
     * 执行的方法
     *
     * @param messageContext
     * @param message
     */
    @Override
    @SneakyThrows
    public void invoke(MessageContext messageContext, Message message) {
        Assert.notNull(message, "CmdInvoker模块执行了且消息不能为空");
        Map<String, CmdHandler> handlerMap = ReceiverHandlerManager.getHandlerMap();
        CmdHandler flowHandler = handlerMap.get(message.getId());
        if(ObjectUtil.isNull(flowHandler)){
            throw new ServiceNotFoundException("没有找到对应的处理类");
        }
        // 由于这里会执行对应的处理类，所以必须++
        messageContext.addCount();
        Message invokeMessage = convertMessage(flowHandler, message);
        try {
            if (ObjectUtil.isNotEmpty(invokeMessage)) {
                flowHandler.invoke(invokeMessage, messageContext);
            } else {
                flowHandler.invoke(message, messageContext);
            }
        }catch (Exception e){
            e.printStackTrace();
            messageContext.addError("msg","服务器内部错误，请稍后重试");
        }
    }

    /**
     * 消息对象转换
     *
     * @param flowHandler
     * @param message
     * @return
     */
    private Message convertMessage(CmdHandler flowHandler, Message message) {
        try {
            Class type = getType(flowHandler);
            return convertFrom(message, type);
        } catch (Exception e) {
            return null;
        }
    }

    //TODO 通过反射获取flowHandler接口上的泛型
    private Class getType(CmdHandler flowHandler) {
        Type[] genericInterfaces = flowHandler.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) genericInterfaces[0];
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        Class<?> genericType = (Class<?>) typeArguments[0];
        return genericType;
    }

    private <K> Message<K> convertFrom(Message message, Class<Message> clazz) throws Exception {
        Message handlerMessage = clazz.newInstance();
        handlerMessage.converterForm(message);
        return handlerMessage;
    }
}
