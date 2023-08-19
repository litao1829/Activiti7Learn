package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.api.client.form.FormDefinitionInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*
* @author lenovo
* @since 2023-06-21 08:31:26
*/
@Data
public class ProcessFormInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private String id;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 流程实例ID
    */
    private String processInstanceId;

    /**
    * 表单ID
    */
    private String formId;

    /**
     * 节点ID
     */
    private String nodeId;

    /**
     * 数据ID
     */
    private String dataId;

    /**
    * 表单类型
    */
    private String formType;

    /**
     * 表单名称
     */
    private String formName;

    /**
     * 租户
     */
    private String tenantId;

    private List<FormDefinitionInfo> fields;
}
