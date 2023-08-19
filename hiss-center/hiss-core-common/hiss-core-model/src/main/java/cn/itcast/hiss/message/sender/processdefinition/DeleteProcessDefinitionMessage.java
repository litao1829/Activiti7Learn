package cn.itcast.hiss.message.sender.processdefinition;

import cn.itcast.hiss.api.client.processdefinition.DeleteProcessDefinition;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * DeleteDeployment
 *
 * @author: wgl
 * @describe: 删除流程定义
 * @date: 2022/12/28 10:10
 */
@Data
public class DeleteProcessDefinitionMessage implements Message<DeleteProcessDefinition> {
    private String id;
    private MessageAuth messageAuth;
    private DeleteProcessDefinition palyload;
    private MessageConfig messageConfig;
}
