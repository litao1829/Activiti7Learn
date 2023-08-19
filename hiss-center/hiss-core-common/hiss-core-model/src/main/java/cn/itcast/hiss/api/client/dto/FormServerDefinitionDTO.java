package cn.itcast.hiss.api.client.dto;

import cn.itcast.hiss.api.client.form.FormDefinitionInfo;
import lombok.Data;

import java.util.List;

/**
 * FormServerDefinitionDTO
 *
 * @author: wgl
 * @describe: 服务端获取表单定义的DTO
 * @date: 2022/12/28 10:10
 */
@Data
public class FormServerDefinitionDTO {


    /**
     * 表单id
     */
    private String id;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单字段
     */
    private List<FormDefinitionInfo> fields;
}
