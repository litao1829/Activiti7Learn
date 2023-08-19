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
* hiss_form_category 实体类
* </p>
*
* @author lenovo
* @since 2023-07-04 10:20:05
*/
@Getter
@Setter
@TableName("hiss_form_category")
public class HissFormCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 分类名称
    */
    private String name;

    /**
    * 流程数量
    */
    private Long num;

    /**
    * 排序值
    */
    private Long ord;

    /**
    * 所属应用
    */
    private String userAppId;


}
