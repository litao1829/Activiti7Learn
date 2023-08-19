package cn.itcast.hiss.cmd.interceptor;

import cn.hutool.core.lang.Assert;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * LogInterceptor
 *
 * @author: wgl
 * @describe: 异步插入数据库
 * @date: 2022/12/28 10:10
 */
@Order(1)
@Async
public class LogInterceptor extends CmdInterceptor {
    @Override
    public void invoke(MessageContext messageContext, Message message) {
        Assert.notNull(message,"LogInterceptor模块执行了且消息不能为空");
    }
}
