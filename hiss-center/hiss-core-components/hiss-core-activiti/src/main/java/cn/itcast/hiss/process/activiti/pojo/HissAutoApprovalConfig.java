package cn.itcast.hiss.process.activiti.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* hiss_auto_approval_config 实体类
* </p>
*
* @author lenovo
* @since 2023-06-06 21:15:58
*/
@Getter
@Setter
@TableName("hiss_auto_approval_config")
@Data
public class HissAutoApprovalConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId
    private Long id;

    /**
    * xml中节点ID
    */
    private String activityId;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 自动完成的操作
    */
    private String operate;

    /**
    * 流程实例ID
    */
    private String processInstanceId;

    /**
    * 任务ID
    */
    private String taskId;

    /**
    * 商户ID
    */
    private String tenantId;


}
