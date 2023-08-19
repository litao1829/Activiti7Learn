package cn.itcast.hiss.api.client.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 回退类型
 */
@AllArgsConstructor
@Getter
public enum RejectType {
    IGNORE_ALL("IGNORE_ALL","忽略全部审核节点"),
    IGNORE_REVIEW("IGNORE_REVIEW","忽略已审核的"),
    ONE_BY_ONE("ONE_BY_ONE","逐级审批"),
    AUTO_REJECT("AUTO_REJECT","自动拒绝"),

    SKIP_FLOW_NODES("SKIP_FLOW_NODES","跳过中间审核节点"),
    ;
    private String type;


    private String des;
}
