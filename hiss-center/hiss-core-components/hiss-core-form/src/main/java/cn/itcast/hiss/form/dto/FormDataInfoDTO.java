package cn.itcast.hiss.form.dto;

import cn.itcast.hiss.common.enums.SearchFieldTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * FormDataInfo
 *
 * @author: wgl
 * @describe: 表单数据项详情
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDataInfoDTO {
    /** 唯一ID */
    private String id;
    /** 单行输入框 */
    private String title;

    private String formId;

    /** 提示语 */
    private String placeholder;

    /** 是否必填 */
    private Boolean mustFlag;

    /**  像素大小：80px  */
    private String width;

    private String type;
    // 名称
    private String lab;
    // 图标
    private String icon;
    // 是否唯一
    private Boolean onlyFlag;
    // 列表显示
    private Boolean listDisplay;
    // 搜索显示
    private Boolean searchDisplay;
    // 搜索类型
    private SearchFieldTypeEnum searchType;
    // 子组件
    private List<FormDataInfoDTO> formData;

    private FormDataInfoDTO data;

}
