package cn.itcast.hiss.api.client.common;

import cn.itcast.hiss.common.enums.InputType;
import lombok.Data;

/**
 * VariableMethod
 *
 * @author: wgl
 * @describe: 变量消息方法  服务端向客户端获取变量内容时需要传递的参数
 * @date: 2022/12/28 10:10
 */
@Data
public class VariableMethod {

    private String variableType;

    private InputType inputType;

    private String ids;

    private String parentId;

    private String type;

    private String query;

    private String tenantId;
}
