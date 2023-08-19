package cn.itcast.hiss.process.activiti.configurator.function;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.activiti.engine.delegate.DelegateExecution;

import java.io.Serializable;

/*
 * @author miukoo
 * @date 2023/7/20 16:53
 * @version 1.0
 **/
public class HissUtil implements Serializable {

    public static boolean hissContainsAny(DelegateExecution execution,String field, String testChars) {
        Object obj = execution.getVariable(field);
        if (obj!=null) {
            String str = obj.toString();

            if (ArrayUtil.contains(testChars.split(","), str)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 获取流程中的流程变量
     * @param execution
     * @param key
     * @return
     */
    public static Object getFormVariable(DelegateExecution execution,String key) {
        if(StrUtil.isEmpty(key)){
            return "";
        }else {
            Object obj = execution.getVariable(key);
            if (obj != null) {
                return obj;
            } else {
                return "";
            }
        }
    }
}
