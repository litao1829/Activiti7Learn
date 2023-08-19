package cn.itcast.hiss.message.sender;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * @author miukoo
 * @description 消息发送者管理器，负责从IOC容器中去查找形成责任链
 * @date 2023/5/12 21:58
 * @version 1.0
 **/
@Log4j2
public class MessageSenderManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    // 责任链跟
    private Sender rootSender;

    public Sender getRootSender(){
        return rootSender;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.scanSender();
    }

    private void scanSender(){
        Map<String, Sender> beans = applicationContext.getBeansOfType(Sender.class);
        // 按照排序值，执行责任链顺序排列
        List<Sender> list = beans.values().stream().sorted(new Comparator<Sender>() {
            @Override
            public int compare(Sender o1, Sender o2) {
                int order1 = o1.order(), order2 = o2.order();
                Order annotation = o1.getClass().getAnnotation(Order.class);
                if (annotation != null) {
                    order1 = annotation.value();
                }
                annotation = o2.getClass().getAnnotation(Order.class);
                if (annotation != null) {
                    order2 = annotation.value();
                }
                return order1 - order2;
            }
        }).collect(Collectors.toList());
        Assert.noNullElements(list,"没有找到任何的消息发送器.....");
        // 初始化责任链
        Sender temp = null;
        int count = 1;
        for (Sender sender : list) {
            log.info("{}扫描到消息发送器：{}",count,sender.getClass().getName());
            if(rootSender==null){
                rootSender=sender;
            }else{
                temp.setNext(sender);
            }
            temp = sender;
        }
    }

}
