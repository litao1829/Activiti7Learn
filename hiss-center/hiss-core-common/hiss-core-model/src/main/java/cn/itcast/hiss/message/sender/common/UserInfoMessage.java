package cn.itcast.hiss.message.sender.common;

import cn.itcast.hiss.api.client.common.PageInfo;
import cn.itcast.hiss.api.server.common.UserInfo;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * UserInfoMessage
 *
 * @author: wgl
 * @describe: 获取用户信息的消息
 * @date: 2022/12/28 10:10
 */
@Data
public class UserInfoMessage implements Message<UserInfo> {

    private String id;
    private MessageAuth messageAuth;
    private UserInfo palyload;
    private MessageConfig messageConfig;
}