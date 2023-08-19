package cn.itcast.hiss.api.client.dto;

import lombok.Data;

/**
 * VariablesQueryDTO
 *
 * @author: wgl
 * @describe: 变量查询DTO
 * @date: 2022/12/28 10:10
 */
@Data
public class VariablesQueryDTO extends VariablesBaseDTO {


    /**
     * 查询内容
     */
    private String query;
}
