package cn.itcast.hiss.message.server.server.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.common.dtos.ResponseResultMessage;
import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageBuilder;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.server.message.MessageSessionFactory;
import cn.itcast.hiss.message.server.service.TcpReceiverService;
import cn.itcast.hiss.tcp.SyncTcpMessageLock;
import cn.itcast.hiss.tcp.TcpConstants;
import cn.itcast.hiss.tcp.TcpMessage;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * 服务端处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class HissServerMessageHandler extends SimpleChannelInboundHandler<String> {

    // 有多个线程可能同时接收数据
    private Map<Thread, String> cache = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier("hissMessageReceiverAsyncExecutor")
    private ExecutorService executorService;
    @Autowired
    private TcpReceiverService tcpReceiverService;


    /**
     * 存在数据高并发，数据和包的情况，合包现象：
     * - channelRead0被调用多次，channelReadComplete值调用一次（意思是：多条消息，一次完成通知）
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("开始解析报文:{}", msg);
        log.info("报文长度：" + msg.length());
        String before = cache.get(Thread.currentThread());
        if (!StringUtil.isNullOrEmpty(before)) {
            msg = before + msg;
        }
        // 缓存继续拼接下次的消息内容
        cache.put(Thread.currentThread(), msg);
        if(msg.endsWith( SystemConstant.NETTY_MESSAGE_SUFFIX )){
            // 合包的一条数据接收完毕
            doActionMessage(ctx);
        }
    }

    /**
     * 如果报文完整则执行任务
     * @param ctx
     * @param msg
     */
    private void invokeMessage(ChannelHandlerContext ctx, String msg) {
        if(StrUtil.isEmpty(msg)){
            return;
        }
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String temp = msg.substring(0,msg.length()-SystemConstant.NETTY_MESSAGE_SUFFIX.length());
//                log.info("处理消息：{}",temp);
                //开始将String类型的报文转换为Message类型的对象
                TcpMessage tcpMessage = parseMessage(temp + "");
                // 回复的结果消息
                if(tcpMessage.isResult()){
                    actionReturn(tcpMessage);
                }else{
                    actionReceiver(tcpMessage,ctx);
                }
            }
        });
    }

    private void actionReceiver(TcpMessage tcpMessage,ChannelHandlerContext ctx) {
        Message message = tcpMessage.getMessage();
        MessageSessionFactory.refreshChannel(message.getMessageAuth().getTenant(), ctx);
        if (message == null) {
            log.error("报文解析失败");
            return;
        }
        MessageContext receiver = tcpReceiverService.receiver(message);
        Message resultMessage = MessageBuilder.builder().id(ResponseResultMessage.ID)
                .tenant(message.getMessageAuth().getTenant())
                .build(ResponseResultMessage.class);
        //校验handler是否有把结果返回
        if (receiver.isSuccess() && receiver.getResult() != null) {
            resultMessage.setPalyload(ResponseResult.okResult(receiver.getResult()));
        } else {
            log.error("报文处理失败");
            resultMessage.setPalyload(ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR).setData(receiver.getError()));
        }
        //向客户端写回数据，会写时，result必须是true,同时消息id必须和接收时一致
        TcpMessage reuslt = new TcpMessage();
        reuslt.setResult(true);
        reuslt.setMessageId(tcpMessage.getMessageId());
        reuslt.setMessage(resultMessage);
        ctx.writeAndFlush(JSONUtil.parse(reuslt).toString() + SystemConstant.NETTY_MESSAGE_SUFFIX + TcpConstants.MESSAGE_DELIMITER);
    }

    public void actionReturn(TcpMessage tcpMessage){
        SyncTcpMessageLock.noticeResult(tcpMessage);
    }

    /**
     * 处理消息
     * @param ctx
     */
    private void doActionMessage(ChannelHandlerContext ctx){
        invokeMessage(ctx, cache.get(Thread.currentThread()));
        cache.remove(Thread.currentThread());
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        doActionMessage(ctx);
    }

    /**
     * 解析报文
     * <p>
     * 设备不同报文也不同，直接使用json格式传输
     */
    private TcpMessage parseMessage(String body) {
        if (StringUtil.isNullOrEmpty(body)) {
            log.warn("报文为空");
            return null;
        }
        body = body.trim();
        // 其它格式的报文需要解析后放入SmsParamsDTO实体
        TcpMessage tcpMessage = JSON.parseObject(body, TcpMessage.class);
        //校验报文内容    id不能为空  消息配置不能为空  消息授权不能为空
        if (tcpMessage == null || StringUtil.isNullOrEmpty(tcpMessage.getMessageId())) {
            log.error("报文内容异常：{}"+body);
            return null;
        }
        return tcpMessage;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // 当出现异常就关闭连接
        MessageSessionFactory.clearChannel(ctx.channel());
        ctx.channel().close();
        ctx.close();
    }

}
