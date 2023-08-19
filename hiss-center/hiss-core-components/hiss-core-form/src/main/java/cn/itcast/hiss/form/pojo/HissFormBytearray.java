package cn.itcast.hiss.form.pojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
* <p>
* hiss_form_bytearray 实体类
* </p>
*
* @author lenovo
* @since 2023-06-26 14:26:05
*/
@Getter
@Setter
@TableName("hiss_form_bytearray")
public class HissFormBytearray implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private Integer rev;

    private String name;
    private String dataId;

    private String content;


}
