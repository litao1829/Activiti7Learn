package cn.itcast.hiss.process.activiti.configurator.function;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;

import javax.el.FunctionMapper;
import java.lang.reflect.Method;

/*
 * @author miukoo
 * @description 注册hutool的字符串和时间工具类工具类
 * @date 2023/6/6 15:07
 * @version 1.0
 **/
public class StrUtilFunctionProvider implements CustomFunctionProvider {
    @Override
    public void addCustomFunctions(ActivitiElContext activitiElContext) {
        Class<HissUtil> strUtilClass = HissUtil.class;
        Method[] methods = strUtilClass.getMethods();
        for (Method method : methods) {
            if(method.getName().equals("hissContainsAny")) {
                activitiElContext.setFunction("hissUtil", method.getName(), method);
            }
            if(method.getName().equals("getFormVariable")) {
                activitiElContext.setFunction("hissUtil", method.getName(), method);
            }
        }
//        methods = DateUtil.class.getMethods();
//        for (Method method : methods) {
//            activitiElContext.setFunction("dateUtil",method.getName(),method);
//        }
    }
}
