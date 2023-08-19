package cn.itcast.hiss.process.activiti.configurator.function;

import cn.itcast.hiss.process.activiti.util.VariableUtil;
import org.activiti.core.el.ActivitiElContext;
import org.activiti.core.el.CustomFunctionProvider;

import java.lang.reflect.Method;

/**
 * VariableFunctionProvider
 *
 * @author: wgl
 * @describe: 系统内置变量函数
 * @date: 2022/12/28 10:10
 */
public class VariableFunctionProvider implements CustomFunctionProvider {

    //提供内置表达式
    @Override
    public void addCustomFunctions(ActivitiElContext activitiElContext) {
        Class<VariableUtil> variableUtilClass = VariableUtil.class;
        Method[] methods = variableUtilClass.getMethods();
        for (Method method : methods) {
            if(method.getName().equals("hissVarLocal")){
                activitiElContext.setFunction("","hissVarLocal", method);
            }
            if(method.getName().equals("exchange")){
                activitiElContext.setFunction("", method.getName(), method);
            }
        }
    }
}
