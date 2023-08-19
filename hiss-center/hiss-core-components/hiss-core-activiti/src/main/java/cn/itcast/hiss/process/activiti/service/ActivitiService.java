package cn.itcast.hiss.process.activiti.service;

import cn.itcast.hiss.api.client.task.NotificationTask;
import cn.itcast.hiss.common.enums.HissTaskTypeEnum;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.form.FormSubmitDataMessage;
import cn.itcast.hiss.message.sender.task.*;
import cn.itcast.hiss.process.activiti.pojo.HissAutoApprovalConfig;
import cn.itcast.hiss.process.activiti.vo.MultiInstanceVo;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * activiti 的业务实现
 *
 * @author: miukoo
 * @describe: activiti
 * @date: 2022/12/28 10:10
 */
public interface ActivitiService {

    Set<String> doGetClientUserInfo(DelegateExecution execution, String assignee);

}
