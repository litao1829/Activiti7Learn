package cn.itcast.hiss.message.sys.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* hiss_process_category 实体类
* </p>
*
* @author lenovo
* @since 2023-06-25 08:48:47
*/
@Getter
@Setter
public class ProcessCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
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
    * 排序值
    */
    private Long ord;

    /**
    * 所属应用
    */
    private String userAppId;


}
