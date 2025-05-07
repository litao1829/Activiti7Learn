package cn.itcast.hiss.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/*
 * 统计订单用到的表
 **/
@Mapper
public interface OrderMapper {

    @Select("select case when status=0 then '待支付' when status=1 then '待执行' when status=2 then '已执行' when status=3 then '已完成' when status=4 then '已关闭' when status=5 then '已退款' end as name, sum(amount) as amount  from ts_order group by name")
    public List<Map<String, Double>> getAmount();

}
