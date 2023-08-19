package cn.itcast.hiss.client.scan;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.manager.HissSubmitHandlerManager;
import cn.itcast.hiss.client.manager.HissVariableHandlerManager;
import cn.itcast.hiss.client.variables.VariablesHandler;
import cn.itcast.hiss.submit.SubmitTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * HissSpelHandlerApplicationAware
 *
 * @author: wgl
 * @describe: 客户端表达式处理器预加载类
 * @date: 2022/12/28 10:10
 */
@Slf4j
public class HissVariableHandlerScan implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext ctx;

    /**
     * 扫描了所有的消息类处理器
     * 还需要扫描所有的重连方法
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("消息处理类加载器启动");
        //扫描所有的变量
        Map<String, VariablesHandler> map = ctx.getBeansOfType(VariablesHandler.class);//拿到IOC容器中所有的MsgHandler的类
        map.values().stream().forEach(v -> {
            try {
                HissVariables annotation = v.getClass().getAnnotation(HissVariables.class);  //获取注解
                if (annotation != null) {
                    MethodType bean = (MethodType) ctx.getBean(annotation.methodType());
                    if(bean.isImpl()) { // 只有变量配置的变量才上报和显示
                        HissVariableHandlerManager.setVariableHandlerMap(annotation.value(), v);
                        HissVariableHandlerManager.addVariables(annotation);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //扫描所有的上报处理类
        Map<String, SubmitTemplate> submitTemplateMap = ctx.getBeansOfType(SubmitTemplate.class);
        submitTemplateMap.values().stream().forEach(v -> {
            try {
                HissSubmitHandlerManager.addSubmitHandler(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
