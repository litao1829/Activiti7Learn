package cn.itcast.hiss.form.pojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
* <p>
* hiss_form_model 实体类
* </p>
*
* @author lenovo
* @since 2023-06-26 14:26:05
*/
@Getter
@Setter
@TableName("hiss_form_model")
public class HissFormModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 分组
    */
    private String category;

    /**
    * 租户
    */
    private String tenantId;

    /**
    * 表单名称
    */
    private String name;

    /**
    * 版本
    */
    private String version;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
    * 是否有效 1是，0否
    */
    private Integer status;


}
