package cn.itcast.hiss.message.server.server;

import cn.itcast.hiss.message.server.perporties.MessageTcpServerProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PostConstruct;

/**
 * netty 服务启动类
 *
 * @author 传智播客
 */
@Slf4j
public class HissNettyServer implements CommandLineRunner {

    private static HissNettyServer hissNettyServer;

    @PostConstruct
    public void init() {
        hissNettyServer = this;
    }

    @Autowired
    private HissServerInitializer hissServerInitializer;
    @Autowired
    private MessageTcpServerProperties messageTcpServerProperties;

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public void start() {
        mainGroup = new NioEventLoopGroup(2);
        subGroup = new NioEventLoopGroup();
        try {
            server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 10*1024*1024)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 60000)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.SO_RCVBUF, 10*1024*1024) // 设置接收缓冲区大小为 8192 字节
                    .childOption(ChannelOption.SO_SNDBUF, 10*1024*1024) // 设置接收缓冲区大小
                    .childHandler(hissServerInitializer);
            // 绑定端口,开始接收进来的连接
            future = server.bind(messageTcpServerProperties.getPort()).sync();
            log.info("netty服务启动: [port:" + messageTcpServerProperties.getPort() + "]");
            // 等待服务器socket关闭
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty服务启动异常-" + e.getMessage());
            throw new Error("netty服务启动异常-" + e.getMessage()+",port"+ messageTcpServerProperties.getPort());
        } finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }


    @Override
    public void run(String... args) {
        new Thread("Netty") {
            @Override
            public void run() {
                hissNettyServer.start();
            }
        }.start();
    }
}
