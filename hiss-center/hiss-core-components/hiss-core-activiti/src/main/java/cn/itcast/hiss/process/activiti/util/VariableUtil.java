package cn.itcast.hiss.process.activiti.util;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * VariableUtil
 *
 * @author: wgl
 * @describe: 变量工具类
 * @date: 2022/12/28 10:10
 */
public class VariableUtil {

    /**
     * 变量转换
     *
     * @param variableKey
     * @return
     */
    public static String exchange(String variableKey, String range) {
        Map<String, Object> sysVariable = new HashMap<String, Object>(3) {
            {
                put("variableKey", variableKey);
                put("variableRange", range);
                put("variableValue", "${" + variableKey + range + "}");
            }
        };
        return JSON.toJSONString(sysVariable);
    }

    /**
     * 变量转换
     *
     * @param variable
     * @return
     */
    public static List<String> hissVarLocal(String variable) {
        return CollectionUtil.newArrayList(variable.split(","));
    }

    public static String[] getVariables(String expression) {
        Pattern pattern = Pattern.compile("\\$\\{hissVar.exchange\\(\"(.*?)\",\"(.*?)\"\\)\\}");
        Matcher matcher = pattern.matcher(expression);

        if (matcher.find()) {
            String[] variables = new String[2];
            variables[0] = matcher.group(1);
            variables[1] = matcher.group(2);
            return variables;
        }

        return null; // If no variables found
    }

    /**
     * 判断是否是客户端变量
     */
    public static boolean checkIsClientVariable(String expression) {
        Pattern pattern = Pattern.compile("\\$\\{hissVar.exchange\\(\"(.*?)\",\"(.*?)\"\\)\\}");
        Matcher matcher = pattern.matcher(expression);
        return matcher.find();
    }
}
