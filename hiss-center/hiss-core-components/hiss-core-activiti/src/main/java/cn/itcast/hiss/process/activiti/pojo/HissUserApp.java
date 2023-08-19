package cn.itcast.hiss.process.activiti.pojo;
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
* hiss_user_app 实体类
* </p>
*
* @author lenovo
* @since 2023-06-21 20:33:17
*/
@Getter
@Setter
@TableName("hiss_user_app")
public class HissUserApp implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId(type=IdType.INPUT)
    private Long id;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 应用名称
    */
    private String appName;

    /**
    * 应用ID
    */
    private String appId;

    /**
    * 状态
    */
    private Integer status;

    /**
    * 网址
    */
    private String website;

    /**
     * 图标
     */
    private String icon;

    /**
    * 更新时间
    */
    private Date updatedTime;

    /**
     * 用户ID
     */
    private String userId;




}
