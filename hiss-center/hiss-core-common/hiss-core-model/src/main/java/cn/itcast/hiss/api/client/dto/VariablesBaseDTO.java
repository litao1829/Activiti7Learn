package cn.itcast.hiss.api.client.dto;

import lombok.Data;

/**
 * BaseDTO
 *
 * @author: wgl
 * @describe: 变量基础DTO
 * @date: 2022/12/28 10:10
 */
@Data
public class VariablesBaseDTO {

    /**
     * 变量类型
     */
    private String variableType;

    /**
     * 方法坐标
     */
    private String type;
}
