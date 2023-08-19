package cn.itcast.hiss.process.activiti.mapper;

import cn.itcast.hiss.process.activiti.pojo.HissAutoApprovalConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
* <p>
* hiss_auto_approval_config Mapper 接口
* </p>
*
* @author miukoo
* @since 2023-06-06 21:16:37
*/
@Mapper
@Component("HissAutoApprovalConfigMapper")
public interface HissAutoApprovalConfigMapper extends BaseMapper<HissAutoApprovalConfig> {

    @Insert("insert into hiss_auto_approval_config(`id`,`activity_id`,`created_time`,`operate`,`process_instance_id`,`task_id`,`tenant_id`) values " +
            "(#{config.id},#{config.activityId},#{config.createdTime},#{config.operate},#{config.processInstanceId},#{config.taskId},#{config.tenantId})")
    void insertHissAutoApprovalConfig(@Param("config") HissAutoApprovalConfig hissAutoApprovalConfig);


    @Select("select * from hiss_auto_approval_config where process_instance_id = #{processInstanceId} and tenant_id = #{tenantId} and activity_id = #{key}")
    HissAutoApprovalConfig selectByInstanceIdAndKey(@Param("processInstanceId")String processInstance,@Param("tenantId") String tenantId,@Param("key") String taskDefinitionKey);
}
