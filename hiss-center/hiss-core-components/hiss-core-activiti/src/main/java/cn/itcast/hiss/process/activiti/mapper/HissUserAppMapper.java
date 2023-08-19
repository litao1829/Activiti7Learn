package cn.itcast.hiss.process.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
* hiss_user_app Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-06-21 20:33:40
*/
public interface HissUserAppMapper extends BaseMapper<HissUserApp> {

    @Select({"<script>select count(1) from hiss_user_app where app_id=#{appId} <if test='dataId!=null'> and id <![CDATA[<>]]> #{dataId} </if></script>"})
    public int checkID(@Param("dataId") Long dataId,@Param("appId")String appId);

    @Select("select count(1) from hiss_user_app where user_id=#{userId}")
    public int userAppCount(@Param("userId") String userId);

    @Select("select GROUP_CONCAT(app_name  SEPARATOR  ', ')  from hiss_user_app  where user_id=#{userId}")
    public String getUserAppNames(@Param("userId") String userId);

    @Select("select app_id from hiss_user_app  where user_id=#{userId}")
    public List<String> getUserAppIdsList(@Param("userId") String userId);


}
