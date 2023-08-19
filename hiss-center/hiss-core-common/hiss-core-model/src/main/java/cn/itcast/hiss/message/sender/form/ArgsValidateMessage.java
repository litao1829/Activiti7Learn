package cn.itcast.hiss.message.sender.form;

import cn.itcast.hiss.api.client.form.ArgsValidated;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * ArgsValidateMessage
 *
 * @author: wgl
 * @describe: 参数校验消息类
 * @date: 2022/12/28 10:10
 */
@Data
public class ArgsValidateMessage implements Message<ArgsValidated> {
    private static final String ID = HandlerIdClientEnum.FORM_ARG_VALIDATED.getId();
    private String id = ID;
    private MessageAuth messageAuth;
    private ArgsValidated palyload;
    private MessageConfig messageConfig;
}
