package cn.itcast.hiss.message.server.message;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * MessageSource
 *
 * @author: wgl
 * @describe: 客户端连接体
 * @date: 2022/12/28 10:10
 */
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MessageSession {

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 客户端通道
     */
    private Channel channel;


    /**
     * ip
     */
    private String clientIp;

    /**
     * 最后一次活跃时间
     */
    private LocalDateTime lastActiveTime;

    /**
     * 是否已经认证
     */
    private boolean authenticated;

    /**
     * 最后一次认证时间
     */
    private LocalDateTime lastAuthenticatedTime;

    /**
     * 最后一次发送心跳时间
     */
    private LocalDateTime lastHeartbeatTime;


    public MessageSession(String clientId, Channel channel) {
        this.tenantId = clientId;
        this.channel = channel;
        this.lastActiveTime = LocalDateTime.now();
        this.authenticated = false;
        this.lastAuthenticatedTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageSession that = (MessageSession) o;
        return tenantId.equals(that.tenantId) && clientIp.equals(that.clientIp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenantId, clientIp);
    }
}