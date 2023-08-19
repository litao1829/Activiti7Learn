package cn.itcast.hiss.process.activiti.pojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* act_re_model 实体类
* </p>
*
* @author lenovo
* @since 2023-06-29 15:43:38
*/
@Getter
@Setter
@TableName("act_re_model")
public class ActReModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId("id_")
    private String id;

    @TableField("CATEGORY_")
    private String category;

    @TableField("CREATE_TIME_")
    private Date createTime;

    @TableField("DEPLOYMENT_ID_")
    private String deploymentId;

    @TableField("EDITOR_SOURCE_EXTRA_VALUE_ID_")
    private String editorSourceExtraValueId;

    @TableField("EDITOR_SOURCE_VALUE_ID_")
    private String editorSourceValueId;

    @TableField("KEY_")
    private String key;

    @TableField("LAST_UPDATE_TIME_")
    private Date lastUpdateTime;

    @TableField("META_INFO_")
    private String metaInfo;

    @TableField("NAME_")
    private String name;

    @TableField("REV_")
    private Integer rev;

    @TableField("TENANT_ID_")
    private String tenantId;

    @TableField("VERSION_")
    private Integer version;

    private String icon;
    private String bisId;
    private Integer status;
    private String description;


}
