package cn.itcast.hiss.client.handler.event.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * @author miukoo
 * @description 监听一个流程结束事件
 * @date 2023/5/28 15:32
 * @version 1.0
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ActivitiTaskCompleted {

}
