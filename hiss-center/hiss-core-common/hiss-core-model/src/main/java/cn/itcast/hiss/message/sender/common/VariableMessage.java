package cn.itcast.hiss.message.sender.common;

import cn.itcast.hiss.api.client.common.HissVariable;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

import java.util.List;

/**
 * VariableMessage
 *
 * @author: wgl
 * @describe: TODO
 * @date: 2022/12/28 10:10
 */
@Data
public class VariableMessage implements Message<List<HissVariable>> {
    private String id;
    private MessageAuth messageAuth;
    private List<HissVariable> palyload;
    private MessageConfig messageConfig;
}
