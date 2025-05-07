package cn.itcast.hiss.server.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper  {

    @Select("SELECT * FROM ts_goods WHERE (num/stock_count) <0.2 and sales >0")
    public List<Map<String, Object>> listGoods();

    @Select("SELECT COUNT(1) from ts_goods where (num/stock_count)<0.2 and sales>0 ")
    public int countGoods();

    @Update("UPDATE ts_goods SET stock_count=stock_count+#{num},num=num+#{num}  WHERE id = #{id}")
    public int updateStock(int id,int num);
}
