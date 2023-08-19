package cn.itcast.hiss.client.annocations;

import cn.itcast.hiss.api.client.common.AssignTypeEnum;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * HissSpelVariables
 *
 * @author: wgl
 * @describe: 客户端用来处理自定义变量的注解
 * @date: 2022/12/28 10:10
 */
@Documented
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HissVariables {

    /**
     * 表达式名称
     *
     * @return
     */
    String key();

    /**
     * 变量表达式
     *
     * @return
     */
    String value();

    /**
     * 描述
     *
     * @return
     */
    String description();


    /**
     * 最大选择数量
     *
     * @return
     */
    int maxSize() default 0;

    /**
     * 候选类型
     *
     * @return
     */
    AssignTypeEnum[] assignType() default {AssignTypeEnum.ASSIGN};


    /**
     * 方法类型
     *
     * @return
     */
    Class methodType();

}
