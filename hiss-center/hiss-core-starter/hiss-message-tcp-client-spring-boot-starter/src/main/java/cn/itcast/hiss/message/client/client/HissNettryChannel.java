package cn.itcast.hiss.message.client.client;

import cn.itcast.hiss.client.manager.HissSubmitHandlerManager;
import cn.itcast.hiss.common.dtos.Complex;
import cn.itcast.hiss.message.client.perporties.MessageTcpClientProperties;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.tcp.TcpConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/*
 * @author miukoo
 * @description 单机节点连接器
 * @date 2023/7/19 20:43
 * @version 1.0
 **/
@Data
@Slf4j
public class HissNettryChannel {

    String host;
    int port;
    Bootstrap bootstrap;
    MessageTcpClientProperties messageTcpClientProperties;
    MessageTcpSenderProperties messageTcpSenderProperties;
    private Channel channel;
    AtomicInteger connectCount = new AtomicInteger(2);
    Map<String, Complex<Channel,HissNettryChannel>> serverChannel;


    public HissNettryChannel(String host, int port, MessageTcpClientProperties messageTcpClientProperties, MessageTcpSenderProperties messageTcpSenderProperties, Map<String, Complex<Channel,HissNettryChannel>> serverChannel, Bootstrap bootstrap){
        this.host = host;
        this.port = port;
        this.messageTcpClientProperties = messageTcpClientProperties;
        this.messageTcpSenderProperties = messageTcpSenderProperties;
        this.serverChannel = serverChannel;
        this.bootstrap = bootstrap;
    }

    /**
     * 异步支持重连的处理
     */
    public void connectAsync() {
        try {
            ChannelFuture future = bootstrap.connect(host, port);
            future.addListener((ChannelFutureListener) f -> {
                Throwable cause = f.cause();
                if (cause != null) {
                    cause.printStackTrace();
                    if (connectCount.get() <= messageTcpClientProperties.getMaxConnectCount()) {
                        int time = connectCount.get() * messageTcpClientProperties.getRetryInterval();
                        log.info("连接服务器{}:{}，{}s后进行第{}次重试....", host,
                                port,
                                time,
                                connectCount.getAndIncrement());
                        f.channel().eventLoop().schedule(this::connectAsync, time, TimeUnit.SECONDS);
                    } else {
                        log.error("不能成功连接服务器{}:{}，请及时检查服务器连接信息，{}s后进行重试连接",
                                host,
                                port,
                                messageTcpClientProperties.getReconnectionInterval()
                        );
                        f.channel().eventLoop().schedule(this::connectAsync, messageTcpClientProperties.getReconnectionInterval(), TimeUnit.SECONDS);
                    }
                }
                Channel clientChannel = f.channel();
                if (clientChannel != null && clientChannel.isActive()) {
                    log.info("连接服务器成功 !!! {} ", clientChannel.localAddress());
                    channel = clientChannel;
                    serverChannel.put(host+":"+port,new Complex<>(channel,this));
                    writePing();
                }
                //扫描所有的类管理器
                HissSubmitHandlerManager.submitAll();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startConnect() {
        serverChannel.remove(host+":"+port);
        connectCount.set(2);
        this.connectAsync();
    }


    public void writePing() {
        String tds = messageTcpSenderProperties.getSources().keySet().stream().collect(Collectors.joining(","));
        //向服务端发送心跳检测
        channel.writeAndFlush("ping:" + tds + TcpConstants.MESSAGE_DELIMITER);
    }


}
