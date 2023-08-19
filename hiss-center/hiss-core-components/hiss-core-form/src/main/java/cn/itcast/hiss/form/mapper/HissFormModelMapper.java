package cn.itcast.hiss.form.mapper;

import cn.itcast.hiss.form.pojo.HissFormModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
* <p>
* hiss_form_model Mapper 接口
* </p>
*
* @author lenovo
* @since 2023-06-26 14:25:21
*/
public interface HissFormModelMapper extends BaseMapper<HissFormModel> {

    @Select("select count(1) from hiss_form_model where tenant_id=#{tenantId} limit 1")
    public int selectAllByTenantIdInt(@Param("tenantId") String tenantId);

    @Select("select count(1) from hiss_form_model where category=#{categoryId} and tenant_id=#{userAppId} limit 1")
    long countCategoryId(@Param("categoryId") Long categoryId, @Param("userAppId") String userAppId);
}
