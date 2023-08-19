package cn.itcast.hiss.form.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * FormDetailPojo
 *
 * @author: wgl
 * @describe: 表名构建器
 * @date: 2022/12/28 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormDetailPojo extends BasePojo{

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableMsg;


    /**
     * 表单模型id
     */
    private String modelId;

    /**
     * 表字段
     */
    private List<HissFormTableFields> fields = new ArrayList<>();
}
