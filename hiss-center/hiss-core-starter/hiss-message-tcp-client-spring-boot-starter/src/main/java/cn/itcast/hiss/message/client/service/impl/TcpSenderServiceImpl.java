package cn.itcast.hiss.message.client.service.impl;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.client.client.HissNettyClient;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.message.client.perporties.TcpSenderSource;
import cn.itcast.hiss.message.client.service.TcpAuthService;
import cn.itcast.hiss.message.client.service.TcpSenderService;
import cn.itcast.hiss.message.sender.Sender;
import cn.itcast.hiss.tcp.TcpMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Map;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/13 16:03
 * @version 1.0
 **/
public class TcpSenderServiceImpl implements TcpSenderService {

    @Autowired
    private HissNettyClient nettyClient;

    @Autowired
    TcpAuthService tcpAuthService;
    @Autowired
    MessageTcpSenderProperties messageTcpSenderProperties;

    @Override
    public void send(Sender sender, MessageContext messageContext, TcpMessage tcpMessage) {
        Message message = tcpMessage.getMessage();
        String tenant = message.getMessageAuth().getTenant();
        Map<String, TcpSenderSource> sources = messageTcpSenderProperties.getSources();
        TcpSenderSource httpSenderSource = sources.get(tenant);
        Assert.notNull(httpSenderSource, "未找到租户" + tenant + "的授权配置");
        tcpAuthService.authCreate(message, httpSenderSource);
        //使用netty发送消息
        String messageJson = JSON.toJSONString(tcpMessage);
        //首先根据租户获取对应的配置
        nettyClient.sendMessage(messageJson);
    }

}
