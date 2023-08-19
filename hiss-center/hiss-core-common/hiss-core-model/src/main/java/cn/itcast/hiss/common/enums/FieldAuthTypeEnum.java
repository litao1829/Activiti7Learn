package cn.itcast.hiss.common.enums;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * @author miukoo
 * @description 表单授权类型
 * @date 2023/6/6 13:03
 * @version 1.0
 **/
public enum FieldAuthTypeEnum {

    VIEW,EDIT,HIDE;

    public static FieldAuthTypeEnum stringToEnum(String name) {
        for (FieldAuthTypeEnum type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

}
