package cn.itcast.hiss.message.sys;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import cn.itcast.hiss.message.sys.pojo.SystemUser;
import lombok.Data;

/**
 *
 * @author: miukoo
 * @date: 2022/12/28 10:10
 */
@Data
public class SystemUserMessage implements Message<SystemUser> {
    private String id;
    private MessageAuth messageAuth;
    private SystemUser palyload;
    private MessageConfig messageConfig;
}
