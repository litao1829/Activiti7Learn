package cn.itcast.hiss.server.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/*
 * @author miukoo
 * @description 获取Bean的上下文
 * @date 2023/8/22 20:42
 * @version 1.0
 **/
@Service
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext APPLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.APPLICATION_CONTEXT = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }
}
