package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/*
 * @author miukoo
 * @description 搜索字段的搜索类型
 * @date 2023/5/26 15:07
 * @version 1.0
 **/
@AllArgsConstructor
@Getter
public enum SearchFieldTypeEnum {

    EQ("等于"),
    GT("大于"),
    GTE("大于等于"),
    LT("小于"),
    LTE("小于等于"),
    LIKE("模糊"),
    BETWEEN("区间");

    private String name;

}
