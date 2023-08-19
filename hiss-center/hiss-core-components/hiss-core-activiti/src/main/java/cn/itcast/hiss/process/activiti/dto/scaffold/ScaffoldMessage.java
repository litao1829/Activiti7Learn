package cn.itcast.hiss.process.activiti.dto.scaffold;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import lombok.Data;

/**
 *
 * @author: miukoo
 * @date: 2022/12/28 10:10
 */
@Data
public class ScaffoldMessage implements Message<ScaffoldDto> {
    private String id;
    private MessageAuth messageAuth;
    private ScaffoldDto palyload;
    private MessageConfig messageConfig;
}
