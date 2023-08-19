package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @author miukoo
 * @description 标记每个task的类型
 * @date 2023/6/3 11:27
 * @version 1.0
 **/
@AllArgsConstructor
@Getter
public enum HissTaskTypeEnum {
    NOTIFICATION("知会"),
    CC("抄送"),
    SINGLE("单人"),
    MULTIPLE("多人"),
    STARTER("发起人");

    private String name;
}
