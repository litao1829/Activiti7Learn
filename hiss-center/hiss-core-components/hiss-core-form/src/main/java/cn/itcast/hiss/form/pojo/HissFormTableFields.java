package cn.itcast.hiss.form.pojo;
import cn.itcast.hiss.common.enums.SearchFieldTypeEnum;
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
* hiss_form_table_fields 实体类
* </p>
*
* @author lenovo
* @since 2023-06-26 14:26:05
*/
@Getter
@Setter
@TableName("hiss_form_table_fields")
public class HissFormTableFields implements Serializable {
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
    * 模型
    */
    private String modelId;

    /**
     * 字段中文名称
     */
    private String fieldName;

    /**
    * 物理字段名称
    */
    private String physicalName;

    /**
    * 字段类型
    */
    private String fieldType;

    /**
    * 是否只唯一，1是  0 否
    */
    private Boolean isOnly;

    /**
    * 是否列表中显示，1是，0否
    */
    private Boolean listDisplay;

    /**
     * 是否列表可搜索，1是，0否
     */
    private Boolean searchDisplay;

    /**
     * 搜索类型
     */
    private SearchFieldTypeEnum searchType;

    /**
     * 是否有效，1是，0否
     */
    private Integer status;

    /**
     * 字段层级，0层级才能在流程中配置
     */
    private Integer level;

    /**
    * 自动填充值的类型
    */
    private String autoFillType;

    /**
    * 字段关联的表id
    */
    private String tableId;

    /**
    * 控件id
    */
    private String controlId;


}
