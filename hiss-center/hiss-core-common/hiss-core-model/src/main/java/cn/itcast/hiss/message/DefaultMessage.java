package cn.itcast.hiss.message;

import lombok.Data;

import java.util.HashMap;

/**
 * 默认的消息处理来，可用来优先获取消息头，载荷采用HASHMAP存储
 * @author miukoo
 */
@Data
public class DefaultMessage<T> implements Message<T> {
    String id;
    MessageAuth messageAuth;
    T palyload;
    MessageConfig messageConfig;
}
