package cn.itcast.hiss.process.activiti.pojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* <p>
* act_ru_event_subscr 实体类
* </p>
*
* @author lenovo
* @since 2023-06-20 12:37:18
*/
@Getter
@Setter
@TableName("act_ru_event_subscr")
public class ActRuEventSubscr implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    private Integer rev;

    private String eventType;

    private String eventName;

    private String executionId;

    private String procInstId;

    private String activityId;

    private String configuration;

    private Timestamp created;

    private String procDefId;

    private String tenantId;


}
