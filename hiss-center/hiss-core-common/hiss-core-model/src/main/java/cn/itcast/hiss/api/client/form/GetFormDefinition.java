package cn.itcast.hiss.api.client.form;

import lombok.Data;

/**
 * FormDefinition
 *
 * @author: wgl
 * @describe: 表单定义
 * @date: 2022/12/28 10:10
 */
@Data
public class GetFormDefinition {

    /**
     * 类型 0:内部表单 1:外部表单
     */
    private String type;

    /**
     * 查询关键字
     */
    private String query;

    /**
     * 表单定义ID 多个用逗号隔开
     */
    private String ids;

    /**
     * 应用ID
     */
    private String tenantId;

}
