package cn.itcast.hiss.message.sender.processdefinition;

import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * ProcessDesignModelMessage
 *
 * @author: wgl
 * @describe: 流程定义上传消息
 * @date: 2022/12/28 10:10
 */
@Data
public class ProcessDesignModelMessage implements Message<ProcessDesignModel> {

    private String id = HandlerIdClientEnum.PD_FLOW_M_SAVE_MODEL_FOR_DEV.getId();
    private MessageAuth messageAuth;
    private ProcessDesignModel palyload;
    private MessageConfig messageConfig;

}
