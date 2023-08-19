package cn.itcast.hiss.process.activiti.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/*
 * @author miukoo
 * @description 活动信息表
 * @date 2023/6/5 11:04
 * @version 1.0
 **/
public interface HiActinstMapper {

    /**
     * 删除活动历史信息，在任意跳转和
     * @param taskId
     */
    @Delete("delete from act_hi_actinst  where task_id_ = #{taskId}")
    void deleteHiActivityInstByTaskId(@Param("taskId") String taskId);

}
