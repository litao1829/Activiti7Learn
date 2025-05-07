package cn.itcast.hiss.server.order;

import cn.itcast.hiss.server.mapper.GoodsMapper;
import cn.itcast.hiss.server.util.SpringContextHolder;
import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.joda.time.LocalDateTime;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


/**
 * 创建采购单 + 入库的数据
 */
public class BuyServiceTask implements JavaDelegate{
    @Override
    public void execute(DelegateExecution execution) {
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();

        //获取需要补货的商品列表
        GoodsMapper goodsMapper = applicationContext.getBean(GoodsMapper.class);
        List<Map<String, Object>> listGoods = goodsMapper.listGoods();

        //================生成采购清单==================
        StringBuilder list = new StringBuilder();
        for (Map<String,Object> good: listGoods) {
            int sales = Integer.valueOf(good.get("sales").toString());
            sales = (int) (sales * 0.3);
            String temp = String.format("<ul><li>商品：%s</li><li>采购数量：%s</li><li>采购商家：%s</li></ul>",
                    good.get("title"), sales, good.get("purchaser"));
            list.append(temp);
        }

        //设置需要采购的清单
        execution.setVariable("buyContent",list);

        //采购清单的标题：采购错误
        String title = "采购错误" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        execution.setVariable("buyTitle",title);


        //========================仓库入库============
        //拼接的字符串自动使用逗号分隔
        StringJoiner store = new StringJoiner(",");
        for (Map<String,Object> good: listGoods) {
            int sales = Integer.valueOf(good.get("sales").toString());
            sales = (int)(sales * 0.3);
            store.add(good.get("id")+"#"+sales);
        }

        //设置入库商品的库存
        execution.setVariable("storeContent",store.toString());

        throw new BpmnError("500");

    }
}
