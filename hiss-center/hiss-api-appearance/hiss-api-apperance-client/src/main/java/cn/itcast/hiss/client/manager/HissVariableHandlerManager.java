package cn.itcast.hiss.client.manager;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.client.annocations.HissVariables;
import cn.itcast.hiss.client.template.HissClientApperanceTemplate;
import cn.itcast.hiss.client.variables.VariablesHandler;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * HissSpelHandlerManager
 *
 * @author: wgl
 * @describe: 用戶编写的所有的Spel处理类管理器
 * @date: 2022/12/28 10:10
 */
@Slf4j
public class HissVariableHandlerManager{

    private static Map<String, VariablesHandler> variablesHandlerMap = new HashMap<>();

    private static Set<HissVariable> hissVariableSet = new HashSet<>();
    private static List<HissVariables> UEL_LIST = new ArrayList<>();

    @Autowired
    private HissClientApperanceTemplate hissClientApperanceTemplate;


    private static boolean sendFlag = false;


    /**
     * 获取对应的实现类
     *
     * @param type
     * @return
     */
    public static MethodType getMethodTypeByType(String type) {
        for (HissVariable index : hissVariableSet) {
            if (index.getMethodType().getType().equals(type)) {
                return index.getMethodType();
            }
        }
        return null;
    }

    /**
     * 根据spel表达式获取对应的处理类
     *
     * @param key
     * @return
     */
    public static VariablesHandler getVariableHandler(String key) {
        Set<String> strings = variablesHandlerMap.keySet();
        for (String indexSpel : strings) {
            if (key.contains(indexSpel)) {
                return variablesHandlerMap.get(indexSpel);
            }
        }
        return null;
    }

    /**
     * 设置spel表达式和对应的处理类
     *
     * @param spelValue
     * @param spelHandler
     */
    public static void setVariableHandlerMap(String spelValue, VariablesHandler spelHandler) {
        variablesHandlerMap.put(spelValue, spelHandler);
    }

    /**
     * @param annotation
     */
    public static void addVariables(HissVariables annotation) {
        UEL_LIST.add(annotation);
    }

    /**
     * 获取所有的流程变量列表
     * @param
     */
    public static List<HissVariables> getUelList() {
        return UEL_LIST;
    }

    public static Set<HissVariable> getHissVariableSet(){
        return hissVariableSet;
    }

    /**
     * 设置变量
     * @param hissVariables
     */
    public static void setHissVariableSet(Set<HissVariable> hissVariables){
        hissVariableSet = hissVariables;
    }

}
