package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * StatutsType
 * 状态类型
 */
@AllArgsConstructor
@Getter
public enum StatutsType {

    ENABLE(1),
    DISABLE(0);

    int code;
}
