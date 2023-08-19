package cn.itcast.hiss.message.sys;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessInstance;
import lombok.Data;

/**
 *
 * @author: miukoo
 * @date: 2022/12/28 10:10
 */
@Data
public class ProcessInstanceMessage implements Message<ProcessInstance> {
    private String id;
    private MessageAuth messageAuth;
    private ProcessInstance palyload;
    private MessageConfig messageConfig;
}
