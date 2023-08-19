package cn.itcast.hiss.form.dto;

import lombok.Data;

/**
 * BaseFromData
 *
 * @author: wgl
 * @describe: 表单基础信息
 * @date: 2022/12/28 10:10
 */
@Data
public class BaseFromDataDTO {

    private String icon;

    private String color;

    private String formTitle;

    private String groupId;

    private String formDescribe;

    /**
     * 表单类型   0：内部表单  1：外部表单
     */
    private String formType;

}