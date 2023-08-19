package cn.itcast.hiss.execption.auth;

import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import lombok.Data;

/*
 * @author miukoo
 * @description 一般的请求参数错误，具体的参数错误信息见message
 * @date 2023/5/13 22:05
 * @version 1.0
 **/
@Data
public class ParamExecption extends RuntimeException{

    AppHttpCodeEnum code = AppHttpCodeEnum.PARAM_REQUIRE;
    public ParamExecption(String message){
        super(message);
    }

}
