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
import java.util.List;

/**
* <p>
* hiss_form_tables 实体类
* </p>
*
* @author lenovo
* @since 2023-06-26 14:26:05
*/
@Getter
@Setter
@TableName("hiss_form_tables")
public class HissFormTables implements Serializable {
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
    * 表名
    */
    private String tableName;

    /**
    * 表的标识
    */
    private String flag;

    /**
     * 是否有效，1是，0否
     */
    private Integer status;

    /**
    * 关联的表单设计模型
    */
    private String modelId;

    /**
    * 物理表名
    */
    private String tablePhysicalName;

    /**
     * 父表ID
     */
    private String parentId;

    /**
     * 字段ID
     */
    private String fieldId;

    @TableField(exist = false)
    private List<HissFormTableFields> fields;

    @TableField(exist = false)
    private List<HissFormTables> childs;

}
