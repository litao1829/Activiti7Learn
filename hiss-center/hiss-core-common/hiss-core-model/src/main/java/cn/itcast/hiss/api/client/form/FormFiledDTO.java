package cn.itcast.hiss.api.client.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FormFiledDTO
 *
 * @author: wgl
 * @describe: 字段内容
 * @date: 2022/12/28 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormFiledDTO {


    /**
     * 控件id
     */
    private String controlId;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 校验类型
     */
    private String validateType;

}