package cn.itcast.hiss.api.client.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * VariablesResultDTO
 *
 * @author: wgl
 * @describe: 变量结果集返回结果
 * @date: 2022/12/28 10:10
 */
@Data
public class VariablesResultDTO {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 在树上是否可以选中
     */
    private boolean select;

}
