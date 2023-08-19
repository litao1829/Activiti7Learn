package cn.itcast.hiss.message.sender.processdefinition;

import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.api.client.processdefinition.ProcessViewerModel;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * ProcessViewerModelMessage
 *
 * @author: miukoo
 * @describe: 流程预览模式，显示流程状态
 * @date: 2022/12/28 10:10
 */
@Data
public class ProcessViewerModelMessage implements Message<ProcessViewerModel> {

    private String id = HandlerIdClientEnum.PI_GET_INSTANCE_DEV_VIEWER.getId();
    private MessageAuth messageAuth;
    private ProcessViewerModel palyload;
    private MessageConfig messageConfig;

}
