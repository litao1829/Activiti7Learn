package cn.itcast.hiss.sdk.web.oa.exception;

/*
 * @author miukoo
 * @description 抛出请求出错
 * @date 2023/6/30 15:49
 * @version 1.0
 **/
public class HissException extends RuntimeException{

    public HissException(String message){
        super(message);
    }

}
