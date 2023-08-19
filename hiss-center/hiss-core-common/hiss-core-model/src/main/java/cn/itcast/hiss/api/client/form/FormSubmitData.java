package cn.itcast.hiss.api.client.form;

import lombok.*;

import java.util.List;
import java.util.Map;

/**
 *
 * @author: miukoo
 * @describe: 提交数据
 * @date: 2022/12/28 10:10
 */
@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FormSubmitData {

    private String operatorName;

    /**
     * 模型id
     */
    private String modelId;

    /**
     * 数据id
     */
    private String dataId;

    /**
     * 字段ID
     */
    private String controlId;

    /**
     * 表单ID:暂存时使用
     */
    private String formId;

    /**
     * 是否暂存
     */
    private boolean draft = false;

    /**
     * 表单配置
     */
    private List<FormConfigDTO> formConfig;

    /**
     * 表单数据
     */
    private List<FormPageDTO> formData;

}
