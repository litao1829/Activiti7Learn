package cn.itcast.hiss.execption.auth;

import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 授权签名错误，比如http接收端获取到的信息
 * @date 2023/5/13 22:05
 * @version 1.0
 **/
@Data
public class AuthSignExecption extends RuntimeException{

    AppHttpCodeEnum code = AppHttpCodeEnum.SIGN_FAIL;

    public AuthSignExecption(String message){
        super(message);
    }

}
