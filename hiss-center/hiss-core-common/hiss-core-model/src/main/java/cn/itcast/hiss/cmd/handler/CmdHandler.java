package cn.itcast.hiss.cmd.handler;


import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;

/**
 * FlowHandlerTemplate
 *
 * @author: wgl
 * @describe: 流程处理器模板
 * @date: 2023/5/13 18:31
 **/
public interface CmdHandler<T> {



    /**
     * 处理方法
     *
     * @param params
     * @param messageContext
     */
    public void invoke(Message<T> params, MessageContext messageContext);

    /**
     * 获取id
     * @return
     */
    public String getId();

    default boolean isAdmin(Message<T> params){
        return  params.getMessageAuth().getCurrentUser().getUserId().equals(HissProcessConstants.ADMIN_ID);
    }
}
