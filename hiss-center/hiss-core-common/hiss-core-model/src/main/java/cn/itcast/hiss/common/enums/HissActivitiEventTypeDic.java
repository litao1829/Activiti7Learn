package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于说明事件类型的，在程序中不使用
 */
@Deprecated
@AllArgsConstructor
public enum HissActivitiEventTypeDic {

  ENTITY_CREATED("流程定义、流程实例、任务等实体对象被创建时的事件，持久化到数据库时被触发"),
  ENTITY_INITIALIZED("初始化实体对象的ID、名称、状态等，保存到数据库之前触发，比ENTITY_CREATED先触发"),
  ENTITY_UPDATED("实体对象的信息修改和持久化后触发"),
  ENTITY_DELETED("实体从数据库中删除后触发"),
  ENTITY_SUSPENDED("实体对象（流程实例、任务、作业）等被挂起时，会触发"),
  ENTITY_ACTIVATED("实体对象（流程实例、任务、作业）等被激活时，会触发"),
  TIMER_SCHEDULED("流程或任务中的定时器创建和计划调度时，会触发"),
  TIMER_FIRED("流程或任务中的定时器被执行时，会触发"),
  JOB_CANCELED("Job被取消时触发"),
  JOB_EXECUTION_SUCCESS("Job执行成功时触发"),
  JOB_EXECUTION_FAILURE("Job执行失败时触发"),
  JOB_RETRIES_DECREMENTED("Job重试时触发"),
  CUSTOM,
  ENGINE_CREATED("引擎启动并成功初始化时，就会触发该事件"),
  ENGINE_CLOSED("引擎关闭时出货法"),
  ACTIVITY_STARTED("活动开始执行时触发"),
  ACTIVITY_COMPLETED("活动成功执行完成时触发"),
  ACTIVITY_CANCELLED("活动被取消时触发"),
  ACTIVITY_SIGNALED("活动收到一个信号时触发"),
  ACTIVITY_COMPENSATE("活动被补偿时触发"),
  ACTIVITY_MESSAGE_SENT("活动发送消息时触发"),
  ACTIVITY_MESSAGE_WAITING("活动处于等待接收消息的状态时触发，边界、中间或子流程启动消息捕获事件等待的状态"),
  ACTIVITY_MESSAGE_RECEIVED("活动收到消息时触发"),
  ACTIVITY_ERROR_RECEIVED("活动执行失败时触发"),
  HISTORIC_ACTIVITY_INSTANCE_CREATED("活动实例被记录到历史记录中时触发"),
  HISTORIC_ACTIVITY_INSTANCE_ENDED("活动实例完成或取消时触发"),
  SEQUENCEFLOW_TAKEN("流程实例经过一条连线并执行转移时触发"),
  UNCAUGHT_BPMN_ERROR("流程实例执行过程中出现BPMN错误时触发"),
  VARIABLE_CREATED("新变量被创建时触发，全局变量和本地变量"),
  VARIABLE_UPDATED("变量修改时触发，全局变量和本地变量"),
  VARIABLE_DELETED("变量删除时触发，全局变量和本地变量"),
  TASK_CREATED("任务被创建时触发"),
  TASK_ASSIGNED("任务的Assignee属性被设置为特定的参与者时触发"),
  TASK_COMPLETED("任务被标记为完成时触发"),
  PROCESS_STARTED("流程实例开始执行时触发"),
  PROCESS_COMPLETED("流程实例完成时触发"),
  PROCESS_COMPLETED_WITH_ERROR_END_EVENT("流程实例执行过程中遇到错误，并且流程定义中定义了错误结束事件来处理该错误时触发"),
  PROCESS_CANCELLED("流程实例取消时触发"),
  HISTORIC_PROCESS_INSTANCE_CREATED("流程实例完成，并且相关的历史数据被记录时触发"),
  HISTORIC_PROCESS_INSTANCE_ENDED("流程实例完成并进入历史状态时触发"),
  MEMBERSHIP_CREATED("创建一个新的身份成员关系时触发"),
  MEMBERSHIP_DELETED("删除一个成员关系时触发"),
  MEMBERSHIPS_DELETED("删除多个成员关系时触发");

  public static final HissActivitiEventTypeDic[] EMPTY_ARRAY = new HissActivitiEventTypeDic[] {};

  public static HissActivitiEventTypeDic[] getTypesFromString(String string) {
    List<HissActivitiEventTypeDic> result = new ArrayList<HissActivitiEventTypeDic>();
    if (string != null && !string.isEmpty()) {
      String[] split = StringUtils.split(string, ",");
      for (String typeName : split) {
        for (HissActivitiEventTypeDic type : values()) {
          if (typeName.equals(type.name())) {
            result.add(type);
            break;
          }
        }
      }
    }
    return result.toArray(EMPTY_ARRAY);
  }
  HissActivitiEventTypeDic(){}
  String name;
}
