package cn.itcast.hiss.form.dto;

import lombok.Data;

import java.util.List;

/**
 * FormDetail
 *
 * @author: wgl
 * @describe: 表单结构数据
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDetailDTO {

    private BaseFromDataDTO baseFormData;

    private List<FormDataDTO> formData;
}
