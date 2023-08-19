package cn.itcast.hiss.common.dtos;

import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import cn.itcast.hiss.handler.HandlerIdServerEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通用的结果返回类
 *
 */
@Data
public class ResponseResultMessage implements Message<ResponseResult> {
    public final static String ID = "RRM";
    private String id = ID;
    MessageAuth messageAuth;
    ResponseResult palyload;
    MessageConfig messageConfig;
}
