package cn.itcast.hiss.tcp;

import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.common.dtos.ResponseResultMessage;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/*
 * @author miukoo
 * @description 用于同步发送结果的实现
 * @date 2023/5/26 20:01
 * @version 1.0
 **/
@Data
@Slf4j
public class SyncTcpMessageLock {

    private static ConcurrentHashMap<String, SyncTcpMessageLock> MESS_QUEUE = new ConcurrentHashMap<>();

    MessageContext messageContext;
    TcpMessage tcpMessage;
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public SyncTcpMessageLock(MessageContext messageContext, TcpMessage tcpMessage){
        this.messageContext = messageContext;
        this.tcpMessage = tcpMessage;
    }

    /**
     * 开始超时等待
     * @param readTimeOut
     */
    public void lock(int readTimeOut){
        try {
            MESS_QUEUE.put(tcpMessage.getMessageId(),this);
            long time = System.currentTimeMillis();
            boolean await = countDownLatch.await(readTimeOut, TimeUnit.MILLISECONDS);
            if(!await){
                messageContext.addError("msg","读取数据超时[readTimeOut:"+readTimeOut+"][实际时间："+(System.currentTimeMillis()-time)+"]");
            }
        } catch (InterruptedException e) {
            messageContext.addError("msg",e);
        }finally {
            MESS_QUEUE.remove(tcpMessage.getMessageId());
        }
    }

    /**
     * 唤醒等待的线程
     */
    public void notice(){
        try {
            countDownLatch.countDown();
        }finally {
            MESS_QUEUE.remove(tcpMessage.getMessageId());
        }
    }

    public static void noticeResult(TcpMessage tcpMessage){
        SyncTcpMessageLock syncTcpMessageLock = MESS_QUEUE.get(tcpMessage.getMessageId());
        if(syncTcpMessageLock!=null){
            Message message = tcpMessage.getMessage();
            if(message!=null){
                ResponseResultMessage resultMessage = new ResponseResultMessage();
                try {
                    resultMessage.converterForm(message);
                    ResponseResult result = resultMessage.getPalyload();
                    if(result.isSuccess()){
                        Map<String,Object> data = (Map<String, Object>) result.getData();
                        if(data!=null){
                            for (String key : data.keySet()) {
                                syncTcpMessageLock.getMessageContext().addResult(key,data.get(key));
                            }
                        }
                    }else{
                        Map<String,Object> data = (Map<String, Object>) result.getData();
                        if(data!=null){
                            for (String key : data.keySet()) {
                                syncTcpMessageLock.getMessageContext().addError(key,data.get(key));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            syncTcpMessageLock.notice();
        }
    }

}
