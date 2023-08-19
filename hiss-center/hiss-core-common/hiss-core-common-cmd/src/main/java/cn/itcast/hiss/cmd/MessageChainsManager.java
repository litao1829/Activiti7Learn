package cn.itcast.hiss.cmd;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.cmd.chains.CmdInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * MessageChainsManager
 *
 * @author: wgl
 * @describe: 流程责任链初始化管理器
 * @date: 2022/12/28 10:10
 */
@Log4j2
public class MessageChainsManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    private CmdInterceptor flowReceiverChains;

    /**
     * 获取链路上的根节点的方法
     *
     * @return
     */
    public CmdInterceptor getRootChains() {
        return flowReceiverChains;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.scanChains();
    }

    /**
     * 扫描所有的流程处理器并加载到链路管理器中
     */
    private void scanChains() {
        Map<String, CmdInterceptor> beans = applicationContext.getBeansOfType(CmdInterceptor.class);
        // 按照排序值，执行责任链顺序排列
        List<CmdInterceptor> list = beans.values().stream().sorted(new Comparator<CmdInterceptor>() {
            @Override
            public int compare(CmdInterceptor o1, CmdInterceptor o2) {
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
        Assert.noNullElements(list, "没有找到任何的服务端方拦截器.....");
        //设置同步异步
        list.forEach(e -> {
            Async hasAsync = e.getClass().getAnnotation(Async.class);
            if (ObjectUtil.isNull(hasAsync)) {
                e.setAsync(false);
            } else {
                e.setAsync(true);
            }
        });
        // 初始化责任链
        CmdInterceptor temp = null;
        int count = 1;
        for (CmdInterceptor chains : list) {
            log.info("{}扫描到服务端拦截器：{},执行方式：{}", count, chains.getClass().getName());
            if (flowReceiverChains == null) {
                flowReceiverChains = chains;
            } else {
                temp.setNext(chains);
            }
            temp = chains;
        }
    }

}