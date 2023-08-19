package cn.itcast.hiss.api.client.form;

import lombok.Data;

import java.util.List;

/**
 * FormDefinition
 *
 * @author: wgl
 * @describe: 表单定义
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDefinition {

    private String id;

    /**
     * 表单名称
     */
    private String name;

    /**
     * 表单数据
     */
    private String context;
}
