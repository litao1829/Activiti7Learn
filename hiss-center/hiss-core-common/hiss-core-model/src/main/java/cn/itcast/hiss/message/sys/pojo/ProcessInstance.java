package cn.itcast.hiss.message.sys.pojo;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import cn.itcast.hiss.common.enums.ProcessStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* </p>
*
* @author lenovo
* @since 2023-06-25 08:48:47
*/
@Getter
@Setter
public class ProcessInstance extends PageRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
    * 主键
    */
    private String id;

    /**
    * 开始时间
    */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 业务编码
     */
    private String businessKey;

    /**
     * 流程定义ID
     */
    private String procInstId;

    /**
     * 流程定义ID
     */
    private String procDefId;

    /**
     * 耗时
     */
    private Integer duration;

    /**
     * 租户
     */
    private String tenantId;

    /**
     * 删除原因
     */
    private String deleteReason;

    /**
     * 发起人ID
     */
    private String startUserId;

    /**
    * 流程名称
    */
    private String name;

    /**
     * 状态
     */
    private ProcessStatusEnum status;

    /**
     * 超级管理员的ID
     */
    private String adminId;

}
