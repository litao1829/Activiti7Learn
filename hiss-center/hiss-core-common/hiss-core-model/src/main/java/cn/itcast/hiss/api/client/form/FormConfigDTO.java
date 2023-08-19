package cn.itcast.hiss.api.client.form;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * FormPageDTO
 *
 * @author: wgl
 * @describe: 每页数据对象
 * @date: 2022/12/28 10:10
 */
@Data
public class FormConfigDTO {

    private String id;
    private String name;
    private String nodeId;

    private List<FormConfigFieldDTO> fields;
}
