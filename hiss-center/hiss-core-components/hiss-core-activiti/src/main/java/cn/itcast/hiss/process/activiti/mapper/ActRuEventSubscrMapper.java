package cn.itcast.hiss.process.activiti.mapper;

import cn.itcast.hiss.process.activiti.pojo.ActRuEventSubscr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* <p>
* act_ru_event_subscr Mapper 接口
* </p>
*
* @author miukoo
* @since 2023-06-20 12:36:12
*/
public interface ActRuEventSubscrMapper  {

    @Select("select * from act_ru_event_subscr where event_type_='message' and PROC_INST_ID_=#{processInstanceId} and TENANT_ID_=#{tenantId}")
    public List<ActRuEventSubscr> selectMessageByProcAndTenant(@Param("processInstanceId") String processInstanceId,@Param("tenantId") String tenantId );

    @Select("select * from act_ru_event_subscr where event_type_='signal' and PROC_INST_ID_=#{processInstanceId} and TENANT_ID_=#{tenantId}")
    public List<ActRuEventSubscr> selectSignalByProcAndTenant(@Param("processInstanceId") String processInstanceId,@Param("tenantId") String tenantId );

}
