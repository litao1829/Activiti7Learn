package cn.itcast.hiss.process.activiti.mapper;

import cn.itcast.hiss.process.activiti.pojo.HissSystemUser;
import cn.itcast.hiss.process.activiti.pojo.HissUserApp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
* hiss_user_app Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-06-21 20:33:40
*/
public interface HissSystemUserMapper extends BaseMapper<HissSystemUser> {

    @Select("<script>select count(*) from hiss_system_user where username=#{username} <if test='dataId!=null'> and id<![CDATA[<>]]>#{dataId} </if></script>")
    public int checkUsername(@Param("dataId") String dataId,@Param("username")String username);


    @Select("select * from hiss_system_user where username=#{username}")
    HissSystemUser selectByUsername(@Param("username")String username);

    @Select("select u.* " +
            "from hiss_user_app a " +
            "    left join hiss_system_user u on a.user_id = u.id " +
            "where u.id = #{id} " +
            "    or (a.app_id = #{tenantId} " +
            "        and u.external_admin_id = #{id} ) limit 1")
    HissSystemUser selectByIdAndTenantId(@Param("id")String id,@Param("tenantId")String tenantId);
}
