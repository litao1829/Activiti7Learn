package cn.itcast.hiss.cmd;

import cn.itcast.hiss.cmd.handler.CmdHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * ReceiverHandlerManager
 *
 * @author: wgl
 * @describe: 流程处理器管理类
 * @date: 2022/12/28 10:10
 */

public class ReceiverHandlerManager {

    /**
     * key为id value为对应的流程处理器
     */
    private static Map<String, CmdHandler> handlerMap = new HashMap<String, CmdHandler>();

    /**
     * 获取处理器
     *
     * @return
     */
    public static Map<String, CmdHandler> getHandlerMap() {
        return handlerMap;
    }

    /**
     * 设置处理器
     *
     * @param id
     * @param handler
     */
    public static void setHandler(String id, CmdHandler handler) {
        handlerMap.put(id, handler);
    }
}
