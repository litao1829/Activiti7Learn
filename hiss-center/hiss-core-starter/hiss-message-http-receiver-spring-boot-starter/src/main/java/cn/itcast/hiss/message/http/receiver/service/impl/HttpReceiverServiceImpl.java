package cn.itcast.hiss.message.http.receiver.service.impl;

import cn.itcast.hiss.cmd.CmdExecuter;
import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.http.receiver.properties.HttpReceiverSource;
import cn.itcast.hiss.message.http.receiver.properties.MessageHttpReceiverProperties;
import cn.itcast.hiss.message.http.receiver.service.HttpAuthCheckService;
import cn.itcast.hiss.message.http.receiver.service.HttpReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import cn.itcast.hiss.message.MessageContext;
/*
 * @author miukoo
 * @description 实现消息的REST接收与执行
 * @date 2023/5/13 16:03
 * @version 1.0
 **/
public class HttpReceiverServiceImpl implements HttpReceiverService {

    @Autowired
    HttpAuthCheckService httpAuthService;
    @Autowired
    MessageHttpReceiverProperties messageHttpReceiverProperties;

    @Autowired
    private CmdExecuter cmdExecutor;

    @Override
    public MessageContext receiver(DefaultMessage message, HttpServletRequest request, HttpServletResponse response) {
        String tenant = message.getMessageAuth().getTenant();
        Map<String, HttpReceiverSource> sources = messageHttpReceiverProperties.getSources();
        HttpReceiverSource httpReceiverSource = sources.get(tenant);
        Assert.notNull(httpReceiverSource,"未找到租户"+tenant+"的授权配置");
        httpAuthService.authCheck(request,response,message,httpReceiverSource);
        return cmdExecutor.executer(message);
    }
}
