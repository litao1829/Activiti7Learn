package cn.itcast.hiss.cmd.interceptor;

import cn.hutool.core.lang.Assert;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import org.springframework.core.annotation.Order;

/**
 * AuthPreInterceptor
 *
 * @author: wgl
 * @describe: 前置身份拦截器--优先级最高
 * @date: 2022/12/28 10:10
 */
@Order(0)
public class AuthPreInterceptor extends CmdInterceptor {

    /**
     * 权限拦截的方法
     *
     * @param messageContext
     * @param message
     */
    @Override
    public void invoke(MessageContext messageContext, Message message) {
        Assert.notNull(message, "AuthPreInterceptor模块执行了且消息不能为空");
    }
}
