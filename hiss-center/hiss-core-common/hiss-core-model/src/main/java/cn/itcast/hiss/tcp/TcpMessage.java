package cn.itcast.hiss.tcp;

import cn.itcast.hiss.message.Message;
import lombok.Data;

/*
 * @author miukoo
 * @description 包装TCP通信的消息
 * @date 2023/5/26 20:04
 * @version 1.0
 **/
@Data
public class TcpMessage {
    String messageId;
    Message message;
    boolean result;//是否是回复的结果消息
}
