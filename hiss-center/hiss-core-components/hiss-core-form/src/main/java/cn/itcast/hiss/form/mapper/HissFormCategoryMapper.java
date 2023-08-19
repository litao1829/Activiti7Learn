package cn.itcast.hiss.form.mapper;

import cn.itcast.hiss.form.pojo.HissFormCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
* hiss_form_category Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-07-04 10:20:27
*/
public interface HissFormCategoryMapper extends BaseMapper<HissFormCategory> {

    @Select("<script>select count(*) from hiss_form_category where name=#{name} and user_app_id=#{userAppId} <if test='dataId!=null'> and id<![CDATA[<>]]>#{dataId} </if></script>")
    int checkName(@Param("dataId") Long dataId, @Param("name") String name,@Param("userAppId") String userAppId);

}
