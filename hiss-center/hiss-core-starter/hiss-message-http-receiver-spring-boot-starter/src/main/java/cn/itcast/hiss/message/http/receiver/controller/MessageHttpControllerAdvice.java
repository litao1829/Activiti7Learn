package cn.itcast.hiss.message.http.receiver.controller;

import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import cn.itcast.hiss.execption.auth.AuthSignExecption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
@ControllerAdvice(basePackages = "cn.itcast.hiss.message.http.receiver.controller")
@Order(Ordered.LOWEST_PRECEDENCE)
public class MessageHttpControllerAdvice {

    @ExceptionHandler(AuthSignExecption.class)
    @ResponseBody
    public ResponseResult caseAuthSignExecption(AuthSignExecption e) {
        log.error(e.getLocalizedMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseResult caseIllegalArgumentException(IllegalArgumentException e) {
        log.error(e.getLocalizedMessage(), e);
        HashMap<String,String> map = new HashMap<>();
        map.put("msg",e.getMessage());
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult caseException(Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR);
    }
}
