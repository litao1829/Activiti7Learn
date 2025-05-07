package cn.itcast.hiss.server.order;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.server.mapper.GoodsMapper;
import cn.itcast.hiss.server.util.SpringContextHolder;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * 更新库存
 */
public class UpdateStockServiceTask implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        //获取入库的数据，并更新到数据库
        String storeContents = (String)execution.getVariable("storeContent");

        //获取Mapper对象
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        GoodsMapper goodsMapper = applicationContext.getBean(GoodsMapper.class);

        //获取事务管理器,配置和获取 Spring 事务管理器，并定义事务的基本属性。
        DataSourceTransactionManager dataSourceTransactionManager = applicationContext.getBean(DataSourceTransactionManager.class);
        DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();

        defaultTransactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        //开启事务
        TransactionStatus status = dataSourceTransactionManager.getTransaction(defaultTransactionDefinition);

        try {
            //分割需要入库的数据
            if(StrUtil.isNotBlank(storeContents)){
                String[] splits = storeContents.split(",");
                int i =0;
                for (String str: splits) {
                    if(i==1){
                        throw new RuntimeException("运行时异常");
                    }
                    String[] content = str.split("#");
                    goodsMapper.updateStock(Integer.valueOf(content[0]),Integer.valueOf(content[1]));
                    i++;
                }
            }
            dataSourceTransactionManager.commit(status);// 提交事务

        } catch (NumberFormatException e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(status);//回滚事务
        }
    }
}
