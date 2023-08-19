package cn.itcast.hiss.message.server.message;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * MessageSourceFactory
 *
 * @author: wgl
 * @describe: 客户端连接体工厂类
 * @date: 2022/12/28 10:10
 */
@Slf4j
public class MessageSessionFactory {
    private static final Map<String, Set<MessageSession>> CLIENT_SESSION_MAP = new ConcurrentHashMap<>();
    private static final Map<Channel, Set<String>> CHANNEL_TENANTID_MAP = new ConcurrentHashMap<>();

    /**
     * TODO 缺少清理程序，清理过期的客户端连接
     */
    public static void refreshChannel(String tenantId, ChannelHandlerContext ctx) {
        Set<MessageSession> clients = CLIENT_SESSION_MAP.getOrDefault(tenantId, new HashSet<MessageSession>());
        Channel channel = ctx.channel();
        Set<String> names = CHANNEL_TENANTID_MAP.getOrDefault(channel, new HashSet<>());

        MessageSession clientInfo = new MessageSession(tenantId, channel);
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        clientInfo.setClientIp(clientIp);
        clients.add(clientInfo);
        CLIENT_SESSION_MAP.put(tenantId, clients);
        // 如果已经添加过
        if(!names.contains(tenantId)){
            log.info("welcome tenantId: {} ==> {}", tenantId,channel);
        }
        // 记录通道绑定了那些租户
        names.add(tenantId);
        CHANNEL_TENANTID_MAP.put(channel,names);

    }

    /**
     * 清理客户端信息
     * @param channel
     */
    public static void clearChannel(Channel channel) {
        Set<String> names = CHANNEL_TENANTID_MAP.get(channel);
        if(names!=null){
            for (String name : names) {
                CLIENT_SESSION_MAP.remove(name);
                log.info("goodbye tenantId: {}", name);
            }
        }
    }

    /**
     * 在ping,pong时注册客户端信息
     * @param ping
     * @param ctx
     */
    public static void registerByPing(String ping,ChannelHandlerContext ctx) {
        ping = ping.replace("ping:","");
        String[] split = ping.split(",");
        for (String tenantId : split) {
            refreshChannel(tenantId,ctx);
        }
    }

    /**
     * 获取客户端连接
     * 根据租户id获取客户端连接
     * 获取租户id对应的客户端连接，按照最后一次活跃时间排序
     *
     * @param tenantId
     * @return
     */
    public static List<Channel> getChannels(String tenantId) {
        Set<MessageSession> clients = CLIENT_SESSION_MAP.getOrDefault(tenantId, new HashSet<MessageSession>());
        return clients.stream().sorted((c1, c2) -> c2.getLastActiveTime().compareTo(c1.getLastActiveTime())).
                map(MessageSession::getChannel).collect(Collectors.toList());
    }

}

