package cn.itcast.hiss.message.sender.impl;

import cn.itcast.hiss.message.MessageCallback;
import cn.itcast.hiss.message.MessageConfig;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.MessageSenderExecuter;
import cn.itcast.hiss.message.sender.MessageSenderManager;
import cn.itcast.hiss.message.sender.Sender;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.sender.properties.MessageSenderAsyncExecutorProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import java.util.concurrent.*;

/*
 * @author miukoo
 * @description 默认的消息发送器实现类
 * @date 2023/5/12 21:56
 * @version 1.0
 **/
@Log4j2
public class DefaultMessageSenderExecuterImpl implements MessageSenderExecuter, DisposableBean {

    @Autowired
    MessageSenderManager messageSenderManager;

    @Autowired
    @Qualifier("messageSenderAsyncExecutor")
    ExecutorService executorService;

    @Autowired
    MessageSenderAsyncExecutorProperties messageSenderAsyncExecutorProperties;

    /**
     * 先递归调用责任链
     * 然后循环等待异步执行的结果
     * @param message
     * @return
     */
    @Override
    public void sendMessage(Message message, MessageCallback messageCallback) {
        innerSendMessage(message,messageCallback);
    }

    /**
     * 内部执行任务
     * 1、如果有异步任务，则异步去检查和等待结果，会回调发起放
     * @param message
     * @param messageCallback
     * @return
     */
    private MessageContext innerSendMessage(Message message, MessageCallback messageCallback) {
        Assert.notNull(message,"发送的消息不能为空");
        MessageContext messageContext = createMessageContext();
        executer(messageSenderManager.getRootSender(),message,messageContext);
        // 异步检查执行结果，并回调
        if(messageContext.getFutures().size()>0){
            executorService.submit(()->this.checkResult(messageContext,messageCallback,message));
        }
        // TODO 调用日志API记录日志
        return messageContext;
    }

    /**
     * 先递归调用责任链
     * 然后循环等待异步执行的结果
     * @param message
     * @return
     */
    @Override
    public MessageContext sendMessage(Message message) {
        return innerSendMessage(message,null);
    }

    /**
     * 检查异步执行的结果
     * @param messageContext
     */
    private void checkResult(MessageContext messageContext,MessageCallback messageCallback,Message message){
        int count =0;
        while (messageContext.getFutures().size()>0){
            Future<?> future = messageContext.getFutures().get(0);
            try {
                if(!future.isDone()) {
                    future.get(messageSenderAsyncExecutorProperties.getExecutorTimeout(), TimeUnit.MILLISECONDS);
                }
            } catch (Exception e) {
                e.printStackTrace();
                messageContext.addError("error"+count++,e);
            }finally {
                messageContext.removeFuture(future);
            }
        }
        if(messageCallback!=null){
            messageCallback.callback(messageContext,message);
        }
    }

    /**
     * 同步或异步真正执行的操作方法，可以做前后置的任务拦截
     * @param sender
     * @param message
     * @param messageContext
     */
    private void sendAction(Sender sender,Message message,MessageContext messageContext){
        try{
            log.debug("开始发送{}类型的消息",sender.typeName());
            sender.send(messageContext,message);
        }catch (Exception e){
            e.printStackTrace();
            messageContext.addError(sender.typeName(),e);
        }
    }

    /**
     * 递归执行消息的发送
     * @param sender
     * @param message
     * @param messageContext
     */
    private void executer(Sender sender,Message message,MessageContext messageContext){
        if (sender!=null){
            MessageConfig messageConfig = message.getMessageConfig();
            if(messageConfig!=null) {
                if (messageConfig.canSend(sender.typeName())) {
                    // 如果设置有错误立即返回，则不需要启动后续的任务了
                    if(!messageConfig.isOnFirstFailReturn()||(messageConfig.isOnFirstFailReturn()&&!messageContext.hasError())) {
                        messageContext.addCount();// 执行任务，则计数一次
                        if (messageConfig.isSync()) {
                            this.sendAction(sender, message, messageContext);
                        } else {
                            // 异步发送不保障顺序,同时如果异步线程池满了，则也会使用同步线程执行
                            Future<?> future = executorService.submit(() -> this.sendAction(sender, message, messageContext));
                            messageContext.addFuture(future);
                        }
                    }
                }
            }
            executer(sender.getNext(),message,messageContext);
        }
    }

    public MessageContext createMessageContext(){
        return new MessageContext();
    }

    /**
     * 优雅的关闭线程池
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(executorService)));
    }

    /**
     * 利用jvm销毁最后一个线程，来优雅的关闭线程池
     * @param executorService
     */
    private void shutdown(ExecutorService executorService) {
        log.info("开始优雅关闭MessageSender线程池......");
        executorService.shutdown();
        try {
            if(!executorService.awaitTermination(messageSenderAsyncExecutorProperties.getCloseAwaitTime(), TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if(!executorService.awaitTermination(messageSenderAsyncExecutorProperties.getCloseAwaitTime(), TimeUnit.SECONDS)) {
                    System.err.println("Thread pool did not terminate");
                }
            }
        } catch(InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("优雅关闭MessageSender线程池完成。");
    }

}
