package cn.itcast.hiss.client.handler;

import cn.itcast.hiss.client.handler.event.annotation.*;
import cn.itcast.hiss.common.dtos.Complex;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/*
 * @author miukoo
 * @description 统一扫描所有的bean的注解方法
 * @date 2023/5/28 17:20
 * @version 1.0
 **/
@Component
public class HissAnnotationMethodManager implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ApplicationContext applicationContext;

    private static Set<Class> SCAN_ANNOTATION_METHOD_CLASS = new HashSet<>();
    private static HashMap<Class, List<Complex<Method, Object>>> SCAN_ANNOTATION_METHOD_RESULT = new HashMap<>();

    public static List<Complex<Method, Object>> getAnnotationMethods(Class aClass) {
        return SCAN_ANNOTATION_METHOD_RESULT.getOrDefault(aClass, new ArrayList<>());
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, Object> maps = applicationContext.getBeansOfType(Object.class);
        for (Object object : maps.values()) {
            String name = object.getClass().getName();
            if (!name.startsWith("org.springframework") && !name.startsWith("java.") && !name.startsWith("com.fasterxml.") && !name.startsWith("org.apache.") && !name.startsWith("javax.") && !name.startsWith("com.sun.")) {
                for (Method method : object.getClass().getMethods()) {
                    Annotation[] annotations = method.getAnnotations();
                    if (annotations != null) {
                        for (Annotation annotation : annotations) {
                            Class<? extends Annotation> aClass = annotation.annotationType();
                            if (SCAN_ANNOTATION_METHOD_CLASS.contains(aClass)) {
                                List<Complex<Method, Object>> list = SCAN_ANNOTATION_METHOD_RESULT.getOrDefault(aClass, new ArrayList<>());
                                list.add(new Complex<>(method, object));
                                SCAN_ANNOTATION_METHOD_RESULT.put(aClass, list);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建一个bean方法的代理类
     *
     * @param bean
     * @param method
     * @param aClass
     * @param <T>
     * @return
     */
    public static <T> T createProxy(Object bean, Method method, Class<T> aClass) {
        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(bean);
        pfb.setInterfaces(aClass);
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        advisor.addMethodName("onEvent");
        advisor.setAdvice((MethodInterceptor) invocation -> {
            int parameterCount = method.getParameterCount();
            Object result = null;
            if (parameterCount == 0) {
                result = method.invoke(invocation.getThis());
            } else {
                result = method.invoke(invocation.getThis(), invocation.getArguments());
            }
            return result;
        });
        pfb.addAdvisor(advisor);
        return (T) pfb.getObject();
    }

    static {
        SCAN_ANNOTATION_METHOD_CLASS.add(ActivitiProcessStart.class);
        SCAN_ANNOTATION_METHOD_CLASS.add(ActivitiProcessEnd.class);
        SCAN_ANNOTATION_METHOD_CLASS.add(ActivitiTaskCreated.class);
        SCAN_ANNOTATION_METHOD_CLASS.add(ActivitiTaskCompleted.class);
        SCAN_ANNOTATION_METHOD_CLASS.add(ActivitiTaskExpedite.class);
    }

}
