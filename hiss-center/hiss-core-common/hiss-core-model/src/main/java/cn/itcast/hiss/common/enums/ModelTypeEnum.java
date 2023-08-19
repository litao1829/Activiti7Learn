package cn.itcast.hiss.common.enums;

/*
 * @author miukoo
 * @description 设计模型
 * @date 2023/6/6 13:03
 * @version 1.0
 **/
public enum ModelTypeEnum {

    DEV,BIS;

    public static ModelTypeEnum stringToEnum(String name) {
        for (ModelTypeEnum type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return ModelTypeEnum.DEV;
    }

}
