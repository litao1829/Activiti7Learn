package cn.itcast.hiss.api.client.form;

import lombok.Data;

/**
 * FormDefinitionInfo
 *
 * @author: wgl
 * @describe: 表单定义
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDefinitionInfo {


    /**
     * 表单英文名
     */
    private String fieldName;

    /**
     * 表单中文名
     */
    private String fieldLabel;

}
