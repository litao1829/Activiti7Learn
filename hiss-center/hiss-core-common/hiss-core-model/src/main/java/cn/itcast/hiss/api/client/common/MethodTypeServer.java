package cn.itcast.hiss.api.client.common;

import cn.itcast.hiss.common.enums.InputType;
import lombok.Data;

/**
 * MethodTypeServer
 *
 * @author: wgl
 * @describe: 服务端存储的方法类型
 * @date: 2022/12/28 10:10
 */
@Data
public class MethodTypeServer {

    public String type;

    public String name;

    public String description;

    public InputType inputType;
}
