package cn.itcast.hiss.message;

import lombok.Data;

import java.util.List;
import java.util.Set;

/*
 * @author miukoo
 * @description 消息的配置
 * @date 2023/5/12 21:41
 * @version 1.0
 **/
@Data
public class MessageConfig {

    /**
     * 消息要发送的通道
     */
    Set<String> channels;

    // 是否同步发送
    boolean sync = true;

    // 是否在第一次执行时，如果发生错误则立即返回，仅在同步状态下有效
    boolean onFirstFailReturn = true;

    // 是否内部消息
    boolean internal=false;

    /**
     * 判断是否需要当前Sender发送消息
     * @param typeName
     * @return
     */
    public boolean canSend(String typeName){
        if(channels!=null){
            return channels.contains(typeName);
        }
        return false;
    }

}
