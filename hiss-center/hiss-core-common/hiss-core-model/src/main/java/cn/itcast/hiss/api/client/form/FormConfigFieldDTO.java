package cn.itcast.hiss.api.client.form;

import lombok.Data;

import java.util.List;

/**
 *
 * @author: miukoo
 * @describe: 字段
 * @date: 2022/12/28 10:10
 */
@Data
public class FormConfigFieldDTO {

    private String fieldName;
    private String fieldLabel;
    private String type;

}
