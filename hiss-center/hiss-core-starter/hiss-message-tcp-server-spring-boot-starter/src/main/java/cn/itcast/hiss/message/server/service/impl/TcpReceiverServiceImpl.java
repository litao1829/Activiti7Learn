package cn.itcast.hiss.message.server.service.impl;

import cn.itcast.hiss.cmd.CmdExecuter;
import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.server.perporties.MessageTcpReceiverProperties;
import cn.itcast.hiss.message.server.perporties.TcpReceiverSource;
import cn.itcast.hiss.message.server.service.TcpAuthCheckService;
import cn.itcast.hiss.message.server.service.TcpReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;

/*
 * @author miukoo
 * @description 实现消息的REST接收与执行
 * @date 2023/5/13 16:03
 * @version 1.0
 **/
public class TcpReceiverServiceImpl implements TcpReceiverService {

    @Autowired
    TcpAuthCheckService authService;
    @Autowired
    MessageTcpReceiverProperties messageHttpReceiverProperties;

    @Autowired
    private CmdExecuter cmdExecutor;

    @Override
    public MessageContext receiver(Message message) {
        String tenant = message.getMessageAuth().getTenant();
        Map<String, TcpReceiverSource> sources = messageHttpReceiverProperties.getSources();
        TcpReceiverSource httpReceiverSource = sources.get(tenant);
        Assert.notNull(httpReceiverSource, "未找到租户" + tenant + "的授权配置");
        authService.authCheck(message, httpReceiverSource);
        MessageContext executer = cmdExecutor.executer(message);
        return executer;
    }
}
