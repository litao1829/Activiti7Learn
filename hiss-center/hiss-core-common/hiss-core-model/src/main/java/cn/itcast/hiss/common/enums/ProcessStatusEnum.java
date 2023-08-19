package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;

/*
 * @author miukoo
 * @description 流程状态设计
 * @date 2023/5/25 16:55
 * @version 1.0
 **/
@AllArgsConstructor
public enum ProcessStatusEnum {
    PREPARE("预备中"),COMPLETE("已完成"),ACTIVE("进行中"),PENDING("挂起中"),CANCEL("已取消")
    ;
    String name;
}
