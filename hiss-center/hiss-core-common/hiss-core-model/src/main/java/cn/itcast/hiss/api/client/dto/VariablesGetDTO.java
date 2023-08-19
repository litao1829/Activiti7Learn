package cn.itcast.hiss.api.client.dto;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * VariablesGetDTO
 *
 * @author: wgl
 * @describe: 基础变量
 * @date: 2022/12/28 10:10
 */
@Data
public class VariablesGetDTO extends VariablesBaseDTO {

    /**
     * id列表 用逗号分隔
     */
    private String ids;


    public List<String> getId(){
        return Arrays.asList(ids.split(","));
    }
}
