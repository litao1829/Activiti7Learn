package cn.itcast.hiss.message;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * @author miukoo
 * @description 消息模块的上下文
 * @date 2023/5/12 21:52
 * @version 1.0
 **/
@Data
public class MessageContext {
    // 存放的异步任务
    List<Future<?>> futures = new ArrayList<>();
    // 存放每个执行任务的错误信息
    ConcurrentHashMap<String,Object> error = new ConcurrentHashMap<>();
    // 匹配执行的消息个数
    AtomicInteger count = new AtomicInteger(0);
    // 每个节点的正确结果
    ConcurrentHashMap<String,Object> result = new ConcurrentHashMap<>();
    /**
     * 增加一个异步任务
     * @param future
     */
    public void addFuture(Future<?> future){
        futures.add(future);
    }

    /**
     * 删除一个异步任务
     * @param future
     */
    public synchronized void removeFuture(Future<?> future){
        futures.remove(future);
    }

    /**
     * 增加执行的任务计数
     * @return
     */
    public int addCount(){
        return count.getAndIncrement();
    }

    /**
     * 计算错误信息
     * @param name
     * @param e
     */
    public void addError(String name,Object e){
        error.put(name,e);
    }

    /**
     * 增加结果信息
     * @param name
     * @param e
     */
    public void addResult(String name,Object e){
        result.put(name,e);
    }
    /**
     * 增加结果信息
     * @param name
     * @param e
     */
    public void addResultAndCount(String name,Object e){
        addCount();
        result.put(name,e);
    }

    /**
     * 返回是否存在错误信息
     * @return
     */
    public boolean hasError(){
        return error.size()>0;
    }

    public boolean isSuccess(){
        return error.size()==0&&count.get()>0&&result.size()>0;
    }
}
