package cn.itcast.hiss.cmd;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * MessageReceiverHandlerManager
 *
 * @author: wgl
 * @describe: 消息接受者管理器，项目启动的时候会自动扫描所有的流程处理器并添加到处理器管理类中
 * @date: 2023/5/13 22:35
 **/
@Log4j2
public class MessageReceiverHandlerManager implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.scanHandler();
    }


    private void scanHandler() {
        //从IOC容器中获取所有的流程处理器
        Map<String, CmdHandler> beans = applicationContext.getBeansOfType(CmdHandler.class);
        //将所有的流程处理器添加到处理器管理类中
        beans.values().stream().forEach(v -> {
            //key 为流程处理器的id value为流程处理器
            //TODO 这里需要做id的重复校验
            ReceiverHandlerManager.setHandler(v.getId(), v);
        });
    }
}