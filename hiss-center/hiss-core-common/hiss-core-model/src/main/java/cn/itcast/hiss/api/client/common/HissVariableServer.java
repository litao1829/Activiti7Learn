package cn.itcast.hiss.api.client.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * HissVariableServer
 *
 * @author: wgl
 * @describe: 服务端变量
 * @date: 2022/12/28 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HissVariableServer {

    /**
     * 变量名称
     */
    private String key;

    /**
     * 变量表达式
     */
    private String value;

    /**
     * 表达式描述
     */
    private String description;


    /**
     * 最大人数
     */
    private int maxSize;

    /**
     * 候选类型
     */
    private List<AssignTypeEnum> assignTypeEnum;

    /**
     * 方法类型
     */
    private MethodTypeServer methodType;

    public HissVariableServer(String key, String value, String description, int maxSize, List<AssignTypeEnum> newArrayList) {
        this.key = key;
        this.value = value;
        this.description = description;
        this.maxSize = maxSize;
        this.assignTypeEnum = newArrayList;
    }
}
