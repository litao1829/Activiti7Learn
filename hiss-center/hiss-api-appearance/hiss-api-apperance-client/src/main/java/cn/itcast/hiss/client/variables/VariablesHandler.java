package cn.itcast.hiss.client.variables;

import cn.itcast.hiss.api.server.common.UserInfo;

import java.util.List;

/**
 * 变量处理类
 */
public interface VariablesHandler {

    /**
     * 获取变量对应的结果
     * @param userInfo
     * @return
     */
    List<String> invoke(UserInfo userInfo);
}
