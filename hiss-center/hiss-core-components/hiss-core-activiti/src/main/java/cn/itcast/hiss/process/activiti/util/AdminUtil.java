package cn.itcast.hiss.process.activiti.util;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.message.Message;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/6/28 20:47
 * @version 1.0
 **/
public class AdminUtil {

    public static boolean isAdmin(Message message){
        if(message.getMessageAuth().getCurrentUser()!=null&&message.getMessageAuth().getCurrentUser().getUserId()!=null){
            return message.getMessageAuth().getCurrentUser().getUserId().equals(HissProcessConstants.ADMIN_ID);
        }
        return false;
    }
}
