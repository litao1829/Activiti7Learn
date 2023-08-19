package cn.itcast.hiss.common.enums;

import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum HissActivitiEventTypeEnum {

  TIMER_SCHEDULED("流程或任务中的定时器创建和计划调度时，会触发"),
  TIMER_FIRED("流程或任务中的定时器被执行时，会触发"),
  JOB_CANCELED("Job被取消时触发"),
  JOB_EXECUTION_SUCCESS("Job执行成功时触发"),
  JOB_EXECUTION_FAILURE("Job执行失败时触发"),
  JOB_RETRIES_DECREMENTED("Job重试时触发"),
  ACTIVITY_STARTED("活动开始执行时触发"),
  ACTIVITY_COMPLETED("活动成功执行完成时触发"),
  ACTIVITY_CANCELLED("活动被取消时触发"),
  ACTIVITY_SIGNALED("活动收到一个信号时触发"),
  ACTIVITY_COMPENSATE("活动被补偿时触发"),
  ACTIVITY_MESSAGE_SENT("活动发送消息时触发"),
  ACTIVITY_MESSAGE_WAITING("活动处于等待接收消息的状态时触发，边界、中间或子流程启动消息捕获事件等待的状态"),
  ACTIVITY_MESSAGE_RECEIVED("活动收到消息时触发"),
  ACTIVITY_ERROR_RECEIVED("活动执行失败时触发"),
  SEQUENCEFLOW_TAKEN("流程实例经过一条连线并执行转移时触发"),
  TASK_CREATED("任务被创建时触发"),
  TASK_ASSIGNED("任务的Assignee属性被设置为特定的参与者时触发"),
  TASK_COMPLETED("任务被标记为完成时触发"),
  PROCESS_STARTED("流程实例开始执行时触发"),
  PROCESS_COMPLETED("流程实例完成时触发"),
  PROCESS_COMPLETED_WITH_ERROR_END_EVENT("流程实例执行过程中遇到错误，并且流程定义中定义了错误结束事件来处理该错误时触发"),
  PROCESS_CANCELLED("流程实例取消时触发"),
  // ======================= 业务上的事件类型 ================
  USER_EXPEDITE("用户催办");

  public static final HissActivitiEventTypeEnum[] EMPTY_ARRAY = new HissActivitiEventTypeEnum[] {};

  public static HissActivitiEventTypeEnum getTypesFromString(String string) {
    if (string != null && !string.isEmpty()) {
      for (HissActivitiEventTypeEnum type : values()) {
        if (string.equals(type.name())) {
          return type;
        }
      }
    }
    return null;
  }
  HissActivitiEventTypeEnum(){}
  String name;
}
