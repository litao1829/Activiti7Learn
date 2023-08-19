package cn.itcast.hiss.message.client.client;

import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.common.dtos.Complex;
import cn.itcast.hiss.message.client.perporties.MessageTcpClientProperties;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.message.client.server.handler.HissClientInitializer;
import cn.itcast.hiss.tcp.TcpConstants;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HissNettyClient implements CommandLineRunner {

    @Autowired
    MessageTcpClientProperties messageTcpClientProperties;
    @Autowired
    MessageTcpSenderProperties messageTcpSenderProperties;
    @Autowired
    HissClientInitializer hissClientInitializer;
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    Bootstrap bootstrap = new Bootstrap();
    Map<String, Complex<Channel, HissNettryChannel>> serverChannel = new HashMap<>();

    public void startConnect() {
        String[] hosts = messageTcpClientProperties.getHost().split(",");
        for (String host : hosts) {
            if (!serverChannel.containsKey(host)) {
                String[] addr = host.split(":");
                HissNettryChannel hissNettryChannel = new HissNettryChannel(addr[0], Integer.valueOf(addr[1]), messageTcpClientProperties, messageTcpSenderProperties, serverChannel, bootstrap);
                hissNettryChannel.startConnect();
            }
        }
    }

    public void start() {
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, false)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, messageTcpClientProperties.getConnectTimeout())
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 10)
                .handler(hissClientInitializer);
        startConnect();
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(Object message) {
        if (serverChannel.size() > 0) {
            for (Complex<Channel, HissNettryChannel> host : serverChannel.values()) {
                Channel channel = host.getFirst();
                if (channel != null && channel.isActive()) {
                    channel.writeAndFlush(message + SystemConstant.NETTY_MESSAGE_SUFFIX + TcpConstants.MESSAGE_DELIMITER);
                    break;
                }
            }
        } else {
            new RuntimeException("no server");
        }
    }

    /**
     * 关闭连接
     */
    public void close() {
        if (serverChannel.size() > 0) {
            for (Complex<Channel, HissNettryChannel> host : serverChannel.values()) {
                Channel channel = host.getFirst();
                if (channel != null) {
                    channel.close();
                }
            }
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }

    public void writePing(Channel channel) {
        if (serverChannel.size() > 0) {
            for (Complex<Channel, HissNettryChannel> host : serverChannel.values()) {
                Channel tempChannel = host.getFirst();
                if (channel == tempChannel) {
                    host.getSecond().writePing();
                    break;
                }
            }
        } else {
            new RuntimeException("no server");
        }
    }

    public void startSignelConnect(Channel channel) {
        if (serverChannel.size() > 0) {
            for (Complex<Channel, HissNettryChannel> host : serverChannel.values()) {
                Channel tempChannel = host.getFirst();
                if (channel == tempChannel) {
                    host.getSecond().startConnect();
                    break;
                }
            }
        } else {
            new RuntimeException("no server");
        }
    }
}
