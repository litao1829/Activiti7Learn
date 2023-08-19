package cn.itcast.hiss.api.client.processdefinition.viewer;

import cn.itcast.hiss.common.enums.ProcessNodeStatusEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 流程节点，悬浮提示内容
 * @date 2023/5/31 16:02
 * @version 1.0
 **/
@Data
public class ViewerTipInfo {
    // 用户
    String userId;
    // 用户名
    String userName;
    // 开始时间
    String startTime;
    // 结束时间
    String endTime;
    // 耗时
    String duration;
    // 办理类型
    String operateType;
    // 办理备注
    String operateNote;
    // 取消原因
    String cacelReson;
}
