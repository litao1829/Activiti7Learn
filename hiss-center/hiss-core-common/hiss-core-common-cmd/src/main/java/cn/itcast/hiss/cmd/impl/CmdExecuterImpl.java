package cn.itcast.hiss.cmd.impl;


import cn.itcast.hiss.cmd.CmdExecuter;
import cn.itcast.hiss.cmd.MessageChainsManager;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import cn.itcast.hiss.cmd.properties.MessageReceiverAsyncExecutorProperties;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageCallback;
import cn.itcast.hiss.message.MessageContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * CmdExecuterImpl
 *
 * @author: wgl
 * @describe: 拦截器统一入口
 * @date: 2022/12/28 10:10
 */
@Log4j2
public class CmdExecuterImpl implements CmdExecuter, DisposableBean {

    @Autowired
    private MessageChainsManager messageChainsManager;

    @Autowired
    @Qualifier("interceptorExecutor")
    private ExecutorService executorService;

    @Autowired
    private MessageReceiverAsyncExecutorProperties messageReceiverAsyncExecutorProperties;

    /**
     * 同步处理对应的消息
     *
     * @param message
     * @return
     */
    @Override
    public MessageContext executer(Message message) {
        //获取链路上所有的节点
        return doExecuter(message, null);
    }

    /**
     * 异步处理消息
     *
     * @param message
     * @param messageCallback
     */
    @Override
    public void executer(Message message, MessageCallback messageCallback) {
        doExecuter(message, messageCallback);
    }

    /**
     * 开始处理消息
     *
     * @param message
     * @param messageCallback
     * @return
     */
    private MessageContext doExecuter(Message message, MessageCallback messageCallback) {
        Assert.notNull(message, "发送的消息不能为空");
        MessageContext messageContext = createMessageContext();
        executer(messageChainsManager.getRootChains(), message, messageContext);
        // 异步检查执行结果，并回调
        if (messageContext.getFutures().size() > 0) {
            executorService.submit(() -> this.checkResult(messageContext, messageCallback, message));
        }
        // TODO 调用日志API记录日志
        return messageContext;
    }


    /**
     * 根据链路类型来执行链路节点
     *
     * @param chains
     * @param message
     * @param messageContext
     */
    private void executer(CmdInterceptor chains, Message message, MessageContext messageContext) {
        if (chains != null) {
            boolean async = chains.isAsync();
            if (async) {
                //证明是异步的
                // 异步发送不保障顺序,同时如果异步线程池满了，则也会使用同步线程执行
                Future<?> future = executorService.submit(() -> chains.invokeMethod(messageContext, message));
                messageContext.addFuture(future);
            } else {
                //证明是同步的
                chains.invokeMethod(messageContext, message);
            }
            //递归调用自己
            executer(chains.getNext(), message, messageContext);
        }
    }


    /**
     * 检查异步执行的结果
     *
     * @param messageContext
     */
    private void checkResult(MessageContext messageContext, MessageCallback messageCallback, Message message) {
        int count = 0;
        while (messageContext.getFutures().size() > 0) {
            Future<?> future = messageContext.getFutures().get(0);
            try {
                if (!future.isDone()) {
                    future.get(messageReceiverAsyncExecutorProperties.getExecutorTimeout(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
                messageContext.addError("error" + count++, e);
            } finally {
                messageContext.removeFuture(future);
            }
        }
        if (messageCallback != null) {
            messageCallback.callback(messageContext, message);
        }
    }

    /**
     * 构建一个消息上下文
     *
     * @return
     */
    private MessageContext createMessageContext() {
        return new MessageContext();
    }

    /**
     * 资源回收
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(executorService)));
    }

    /**
     * 利用jvm销毁最后一个线程，来优雅的关闭线程池
     *
     * @param executorService
     */
    private void shutdown(ExecutorService executorService) {
        log.info("开始优雅关闭MessageSender线程池......");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(messageReceiverAsyncExecutorProperties.getCloseAwaitTime(), TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(messageReceiverAsyncExecutorProperties.getCloseAwaitTime(), TimeUnit.SECONDS)) {
                    log.error("Thread pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("优雅关闭MessageSender线程池完成。");
    }
}
