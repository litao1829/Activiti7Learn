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
* hiss_system_user 实体类
* </p>
*
* @author lenovo
* @since 2023-06-22 09:27:20
*/
@Getter
@Setter
@TableName("hiss_system_user")
public class HissSystemUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    @TableId(type=IdType.ASSIGN_UUID)
    private String id;

    /**
    * 创建时间
    */
    private Date createdTime;

    /**
    * 用户名
    */
    private String name;

    /**
    * 密码
    */
    private String password;

    private Integer status;

    /**
    * 账户名
    */
    private String username;

    /**
     * 盐
     */
    private String salt;
    /**
     * 备注
     */
    private String note;

    private String externalAdminId;



}
