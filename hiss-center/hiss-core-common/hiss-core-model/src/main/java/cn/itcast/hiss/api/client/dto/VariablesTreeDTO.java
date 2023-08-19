package cn.itcast.hiss.api.client.dto;

import lombok.Data;

/**
 * VariablesTreeDTO
 *
 * @author: wgl
 * @describe: 变量树DTO
 * @date: 2022/12/28 10:10
 */
@Data
public class VariablesTreeDTO extends VariablesBaseDTO{

    /**
     * 父节点id
     */
    private String parentId;

}
