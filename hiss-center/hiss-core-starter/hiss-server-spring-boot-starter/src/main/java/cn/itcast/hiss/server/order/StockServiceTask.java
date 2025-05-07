package cn.itcast.hiss.server.order;

import cn.itcast.hiss.server.mapper.GoodsMapper;
import cn.itcast.hiss.server.util.SpringContextHolder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.context.ApplicationContext;


/**
 * 库存分析
 */
public class StockServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        //获取Spring上下文
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        GoodsMapper goodsMapper = applicationContext.getBean(GoodsMapper.class);
        int countGoods = goodsMapper.countGoods();
        execution.setVariable("goodSize",countGoods);
    }
}
