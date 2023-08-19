package cn.itcast.hiss.message.sender.form;

import cn.itcast.hiss.api.client.form.FormDataList;
import cn.itcast.hiss.api.client.form.FormSubmitData;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 *
 * @author: miukoo
 * @describe: 表单列表数据
 * @date: 2022/12/28 10:10
 */
@Data
public class FormDataListMessage implements Message<FormDataList> {
    private String id = HandlerIdClientEnum.FORM_SUBMIT_DATA.getId();
    private MessageAuth messageAuth;
    private FormDataList palyload;
    private MessageConfig messageConfig;
}
