package cn.itcast.hiss.process.activiti.mapper;

import cn.itcast.hiss.process.activiti.pojo.HissProcessCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
* hiss_process_category Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-06-25 08:49:27
*/
public interface HissProcessCategoryMapper extends BaseMapper<HissProcessCategory> {

    @Select("<script>select count(*) from hiss_process_category where name=#{name} and user_app_id=#{userAppId} <if test='dataId!=null'> and id<![CDATA[<>]]>#{dataId} </if></script>")
    int checkName(@Param("dataId") Long dataId, @Param("name") String name,@Param("userAppId") String userAppId);
}
