package cn.itcast.hiss.api.client.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AssignTypeEnum
 *
 * @author: wgl
 * @describe: 候选类型
 * @date: 2022/12/28 10:10
 */
@AllArgsConstructor
@Getter
public enum AssignTypeEnum {
    ASSIGN("ASSIGN","执行人"),
    CANDIDATE_USER("CANDIDATE_USER","候选人"),
    CANDIDATE_GROUP("CANDIDATE_GROUP","候选组"),
    ;

    private String type;

    private String des;
}
