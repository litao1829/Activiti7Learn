package cn.itcast.hiss.api.client.form;

import lombok.Data;

/**
 * FormPublisk
 *
 * @author: wgl
 * @describe: 表单发布
 * @date: 2022/12/28 10:10
 */
@Data
public class FormPublish {

    /**
     * 表单id
     */
    private String modelId;

    /**
     * 版本号
      */
    private String version;


    /**
     * 表单详情
     */
    private String formDetail;

    /**
     * 物理表名
     */
    private String tableName;

}
