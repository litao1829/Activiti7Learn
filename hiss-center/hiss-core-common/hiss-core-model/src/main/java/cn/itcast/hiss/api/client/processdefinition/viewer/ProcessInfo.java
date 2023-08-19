package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.common.enums.ProcessNodeStatusEnum;
import cn.itcast.hiss.common.enums.ProcessStatusEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 流程基本信息
 * @date 2023/5/31 16:02
 * @version 1.0
 **/
@Data
public class ProcessInfo {
    // 提示标题
    String name;
    // 开始时间
    String startTime;
    // 发起人
    String startUserId;
    // 接收时间
    String endTime;
    // 结束原因
    String endReason;
    // 耗时
    String duration;
    // 流程状态
    ProcessStatusEnum status;
    // 是否是超管
    Boolean admin;
    // 流程模式: 预备模式只有BIS支持，因此默认
    ModelTypeEnum type = ModelTypeEnum.BIS;
    // 应用app
    String tenantId;

    // 知会和抄送要用到的父流程ID
    String parentProcessId;
}
