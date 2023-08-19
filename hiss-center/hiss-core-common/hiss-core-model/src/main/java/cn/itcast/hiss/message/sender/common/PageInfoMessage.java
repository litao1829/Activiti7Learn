package cn.itcast.hiss.message.sender.common;

import cn.itcast.hiss.api.client.common.PageInfo;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageAuth;
import cn.itcast.hiss.message.MessageConfig;
import lombok.Data;

/**
 * PageInfoMessage
 *
 * @author: wgl
 * @describe: 分页消息
 * @date: 2022/12/28 10:10
 */
@Data
public class PageInfoMessage implements Message<PageInfo> {
    private String id;
    private MessageAuth messageAuth;
    private PageInfo palyload;
    private MessageConfig messageConfig;
}
