package cn.itcast.hiss.message.sender.form;

import cn.itcast.hiss.api.client.form.FormPublish;
import cn.itcast.hiss.api.client.form.FormSubmitData;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * FormSubmitDataMessage
 *
 * @author: wgl
 * @describe: 表单提交数据
 * @date: 2022/12/28 10:10
 */
@Data
public class FormSubmitDataMessage implements Message<FormSubmitData> {
    private String id = HandlerIdClientEnum.FORM_SUBMIT_DATA.getId();
    private MessageAuth messageAuth;
    private FormSubmitData palyload;
    private MessageConfig messageConfig;
}
