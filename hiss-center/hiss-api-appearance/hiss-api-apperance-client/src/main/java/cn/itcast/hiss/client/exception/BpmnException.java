package cn.itcast.hiss.client.exception;

import lombok.Data;

/*
 * @author miukoo
 * @description 用于抛出错误异常，给流程传入错误状态码
 * @date 2023/6/20 14:20
 * @version 1.0
 **/
@Data
public class BpmnException extends RuntimeException{
    String errorCode;
    public BpmnException(String errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }
}
