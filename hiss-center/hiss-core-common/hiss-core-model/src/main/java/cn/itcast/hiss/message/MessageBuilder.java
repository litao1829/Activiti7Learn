package cn.itcast.hiss.message;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 构建默认的消息，注意消息的授权信息由消息自身的收发者自行处理和验证
 * @author miukoo
 */
@Data
public class MessageBuilder<T> implements Message<T> {
    String id;
    MessageAuth messageAuth;
    T palyload;
    MessageConfig messageConfig;

    private final static String DEFAULT_CHANNEL="tcp";

    public static MessageBuilder builder(){
        return new MessageBuilder<>();
    }

    public MessageBuilder<T> id(String id){
        this.id = id;
        return this;
    }

    public MessageBuilder<T> tenant(String tenant){
        initMessageAuth();
        messageAuth.setTenant(tenant);
        return this;
    }

    public MessageBuilder<T> palyload(T palyload){
        this.palyload = palyload;
        return this;
    }

    public MessageBuilder<T> authType(MessageAuthType authType){
        initMessageAuth();
        if(authType != MessageAuthType.NONE){
            messageAuth.setEncrypt(false);
            messageAuth.setEncryptContent("");
        }else{
            messageAuth.setEncrypt(true);
            messageAuth.setAuthType(authType);
        }
        return this;
    }

    public MessageBuilder<T> currentUserName(String userName){
        CurrentUser currentUser = getCurrentUser();
        currentUser.setUserName(userName);
        return this;
    }

    public MessageBuilder<T> currentUserId(String userId){
        CurrentUser currentUser = getCurrentUser();
        currentUser.setUserName(userId);
        return this;
    }

    public MessageBuilder<T> currentUser(String userId, String userName){
        CurrentUser currentUser = getCurrentUser();
        currentUser.setUserId(userId);
        currentUser.setUserName(userName);
        return this;
    }

    public MessageBuilder<T> useHttp(){
        Set<String> channels =getChannels();
        channels.contains("http");
        return this;
    }

    public MessageBuilder<T> useTcp(){
        Set<String> channels =getChannels();
        channels.contains("tcp");
        return this;
    }

    public MessageBuilder<T> useMq(){
        Set<String> channels =getChannels();
        channels.contains("mq");
        return this;
    }

    public MessageBuilder<T> useAsync(){
        initMessageConfig();
        messageConfig.setSync(false);
        return this;
    }

    public MessageBuilder<T> useSync(){
        initMessageConfig();
        messageConfig.setSync(true);
        return this;
    }


    public MessageBuilder<T> internal(){
        initMessageConfig();
        messageConfig.setInternal(true);
        return this;
    }

    public MessageBuilder<T> unFirstFailReturn(){
        initMessageConfig();
        messageConfig.setOnFirstFailReturn(false);
        return this;
    }

    public Message<T> build(){
        check();
        DefaultMessage<T> message = new DefaultMessage<>();
        message.setPalyload(this.palyload);
        message.setMessageAuth(this.messageAuth);
        message.setId(this.id);
        message.setMessageConfig(this.messageConfig);
        return message;
    }

    private void check(){
        Assert.hasText(this.id,"消息的唯一标识id是必须的");
        // 如果没有初始化授权的消息，则默认
        initMessageAuth();
        // 如果没设置通道，则默认tcp
        Set<String> channels = getChannels();
        if(channels.size()==0){
            channels.add(DEFAULT_CHANNEL);
        }
        // 判断商户ID是否为null
        Assert.hasText(this.messageAuth.getTenant(),"商户ID是必须");
    }

    public Message<T> build(Class<? extends Message<T>> clazz){
        check();
        Message<T> message = null;
        try {
            message = clazz.newInstance();
            message.setPalyload(this.palyload);
            message.setMessageAuth(this.messageAuth);
            if(StrUtil.isNotEmpty(this.id)){
                message.setId(this.id);
            }
            message.setMessageConfig(this.messageConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


    private Set<String> getChannels(){
        initMessageConfig();
        Set<String> channels = messageConfig.getChannels();
        if(channels==null){
            channels=new HashSet<>();
            messageConfig.setChannels(channels);
        }
        return channels;
    }

    private CurrentUser getCurrentUser(){
        initMessageAuth();
        CurrentUser currentUser = messageAuth.getCurrentUser();
        if(currentUser==null){
            currentUser=new CurrentUser();
            messageAuth.setCurrentUser(currentUser);
        }
        return currentUser;
    }

    private synchronized void initMessageAuth(){
       if(messageAuth==null){
           synchronized (MessageBuilder.class) {
               if(messageAuth==null) {
                   messageAuth = new MessageAuth();
               }
           }
       }
    }

    private synchronized void initMessageConfig(){
        if(messageConfig==null){
            synchronized (MessageBuilder.class) {
                if(messageConfig==null) {
                    messageConfig = new MessageConfig();
                }
            }
        }
    }
}
