package cn.itcast.hiss.message.client.server.handler;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.CmdExecuter;
import cn.itcast.hiss.common.SystemConstant;
import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.common.dtos.ResponseResultMessage;
import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageBuilder;
import cn.itcast.hiss.message.MessageContext;
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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * HissClientMessageHandler
 *
 * @author: wgl
 * @describe: 客户端处理器
 * @date: 2022/12/28 10:10
 */
@ChannelHandler.Sharable
@Slf4j
public class HissClientMessageHandler extends SimpleChannelInboundHandler<String> {

    @Autowired
    @Qualifier("hissMessageReceiverAsyncExecutor")
    private ExecutorService hissMessageReceiverAsyncExecutor;

    @Autowired
    private CmdExecuter cmdExecuter;
    private Map<Thread, String> cache = new ConcurrentHashMap<>();

    public void actionMessage(ChannelHandlerContext ctx, String msg) throws Exception {
        if(StrUtil.isEmpty(msg)){
            return;
        }
        hissMessageReceiverAsyncExecutor.execute(()->{
            String temp = msg.substring(0,msg.length()-SystemConstant.NETTY_MESSAGE_SUFFIX.length());
            log.info("处理消息：{}",temp);
            TcpMessage tcpMessage = JSON.parseObject(temp, TcpMessage.class);
            // 回复的结果消息
            if(tcpMessage.isResult()){
                actionReturn(tcpMessage);
            }else{
                actionReceiver(tcpMessage,ctx);
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
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
     * 处理消息
     * @param ctx
     */
    private void doActionMessage(ChannelHandlerContext ctx) throws Exception {
        actionMessage(ctx, cache.get(Thread.currentThread()));
        cache.remove(Thread.currentThread());
        ctx.flush();
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        doActionMessage(ctx);
    }

    private void actionReceiver(TcpMessage tcpMessage, ChannelHandlerContext ctx) {
        Message message = tcpMessage.getMessage();
        MessageContext executer = cmdExecuter.executer(message);
        Message resultMessage = MessageBuilder.builder().id(ResponseResultMessage.ID)
                .tenant(message.getMessageAuth().getTenant())
                .build(ResponseResultMessage.class);
        //校验handler是否有把结果返回
        if (executer.isSuccess() && executer.getResult() != null) {
            resultMessage.setPalyload(ResponseResult.okResult(executer.getResult()));
        } else {
            resultMessage.setPalyload(ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR).setData(executer.getError()));
        }
        //向客户端写回数据，会写时，result必须是true,同时消息id必须和接收时一致
        TcpMessage reuslt = new TcpMessage();
        reuslt.setResult(true);
        reuslt.setMessageId(tcpMessage.getMessageId());
        reuslt.setMessage(resultMessage);
        ctx.writeAndFlush(JSON.toJSONString(reuslt) + SystemConstant.NETTY_MESSAGE_SUFFIX + TcpConstants.MESSAGE_DELIMITER);
    }

    private void actionReturn(TcpMessage tcpMessage) {
        SyncTcpMessageLock.noticeResult(tcpMessage);
    }
}
