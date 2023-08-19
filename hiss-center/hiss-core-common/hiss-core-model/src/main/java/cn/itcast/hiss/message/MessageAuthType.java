package cn.itcast.hiss.message;

/*
 * @author miukoo
 * @description 消息的授权类型
 * @date 2023/5/12 21:31
 * @version 1.0
 **/
public enum MessageAuthType {
    NONE("无授权"),
    BASIC("basic授权"),
    BEARER("bearer授权"),
    HUTOOL_JWT("hutool生成的JWT验证"),
    JWT_HS256("JWT_HS256验证")
    ;

    String name;

    MessageAuthType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
