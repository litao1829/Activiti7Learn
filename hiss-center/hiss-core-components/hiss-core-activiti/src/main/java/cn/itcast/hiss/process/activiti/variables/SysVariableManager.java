package cn.itcast.hiss.process.activiti.variables;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.api.client.common.HissVariableServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * SysVariableManager
 *
 * @author: wgl
 * @describe: 系统变量管理类
 * @date: 2022/12/28 10:10
 */
public class SysVariableManager {

    //租户id  变量key  变量对象
    private static Map<String,Map<String, HissVariableServer>> SYS_VARIABLE = new ConcurrentHashMap<>();

    public static List<HissVariableServer> getTenantVariable(String tenantId){
        if(SYS_VARIABLE.get(tenantId) == null){
            return new ArrayList<>();
        }
        return SYS_VARIABLE.get(tenantId).values().stream().collect(Collectors.toList());
    }

    public static HissVariableServer getVariable(String tenantId,String key){
        return SYS_VARIABLE.get(tenantId).get(key);
    }

    /**
     * 新增变量
     * @param hissVariable
     */
    public static void addVariable(String tenantId,HissVariableServer hissVariable){
        Map<String, HissVariableServer> stringHissVariableMap = SYS_VARIABLE.get(tenantId);
        if(stringHissVariableMap == null){
            stringHissVariableMap = new HashMap<>();
        }
        stringHissVariableMap.put(hissVariable.getKey(),hissVariable);
        SYS_VARIABLE.put(tenantId,stringHissVariableMap);
    }

    /**
     * 初始化和更新变量
     * @param tenantId
     * @param hissVariables
     */
    public static synchronized void cleanAndSetVariable(String tenantId,List<HissVariableServer> hissVariables){
        //清空原有列表
        Map<String, HissVariableServer> stringHissVariableMap = new HashMap<>();
        for (HissVariableServer hissVariable : hissVariables) {
            stringHissVariableMap.put(hissVariable.getKey(),hissVariable);
        }
        SYS_VARIABLE.remove(tenantId);
        SYS_VARIABLE.put(tenantId,stringHissVariableMap);
    }
}