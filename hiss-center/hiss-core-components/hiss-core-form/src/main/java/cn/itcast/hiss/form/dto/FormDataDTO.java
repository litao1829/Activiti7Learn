package cn.itcast.hiss.form.dto;

import lombok.Data;

import java.util.List;

/**
 * F3ormData
 *
 * @author: wgl
 * @describe: 表单数据项详情
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDataDTO {

    /** 分页表单 */
    private Integer page;

    /** 1:正常控件 2:引用控件 */
    private Integer type;

    private String id;

    private List<FormDataInfoDTO> data;

}