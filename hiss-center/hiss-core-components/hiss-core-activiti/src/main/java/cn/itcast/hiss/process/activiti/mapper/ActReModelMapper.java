package cn.itcast.hiss.process.activiti.mapper;

import cn.itcast.hiss.process.activiti.pojo.ActReModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.security.core.parameters.P;

/**
* <p>
* act_re_model Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-06-29 15:45:46
*/
public interface ActReModelMapper extends BaseMapper<ActReModel> {

    @Update("update act_re_model set icon = #{icon},description=#{description} where id_=#{id}")
    public void updateIconById(@Param("icon") String icon,@Param("id") String id,@Param("description") String description);

    @Update("update act_re_model set bis_id = #{bisId} where id_=#{id}")
    public void updateBisById(@Param("bisId") String bisId,@Param("id") String id);

    @Select("select bis_id from act_re_model  where id_=#{id}")
    public String getModelBusId(@Param("id") String id);

    @Select("select * from act_re_model  where bis_id=#{bisId}")
    public ActReModel getModelByBusId(@Param("bisId") String bisId);
}
