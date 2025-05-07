package cn.itcast.hiss.process.activiti.handler.processinstance;

import cn.hutool.core.io.IoUtil;
import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.api.client.processdefinition.ProcessViewerModel;
import cn.itcast.hiss.api.client.processdefinition.viewer.ProcessInfo;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.common.enums.ProcessStatusEnum;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.processdefinition.ProcessViewerModelMessage;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PI_GetInstanceViewerHandler implements CmdHandler<ProcessViewerModelMessage> {

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessViewerModel processViewerModel = (ProcessViewerModel)params.getPalyload();
        //获取流程实例id
        String processInstanceId = processViewerModel.getProcessInstanceId();
        //获取流程实例对象
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        //获取流程图xml
        getXml(historicProcessInstance,processViewerModel);
        //获取基本信息
        getProcessInfo(historicProcessInstance,processViewerModel);
        //返回数据
        messageContext.addResultAndCount("viewer",processViewerModel);
    }


    /**
     * 查询流程图的xml数据
     * @param historicProcessInstance
     * @param processViewerModel
     */
    public void getXml(HistoricProcessInstance historicProcessInstance,ProcessViewerModel processViewerModel){
        InputStream xmlStream = repositoryService.getResourceAsStream(historicProcessInstance.getDeploymentId(), HissProcessConstants.PROCESS_RUN_XML);
        String xmlStr = IoUtil.readUtf8(xmlStream);
        processViewerModel.setContent(xmlStr);
    }


    /**
     * 加载基本信息
     * @param historicProcessInstance
     * @param processViewerModel
     */
    public void getProcessInfo(HistoricProcessInstance historicProcessInstance,ProcessViewerModel processViewerModel){
        ProcessInfo processInfo = new ProcessInfo();
        //设置值
        processInfo.setName(historicProcessInstance.getName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        processInfo.setStartTime(simpleDateFormat.format(historicProcessInstance.getStartTime()));
        long duration = 0;
        //如果有结束时间
        if(historicProcessInstance.getEndTime() != null){
            //进行时间格式转换
            processInfo.setEndTime(formatDuration(historicProcessInstance.getEndTime().getTime()));
            duration = historicProcessInstance.getEndTime().getTime() - historicProcessInstance.getStartTime().getTime();
            processInfo.setStatus(ProcessStatusEnum.COMPLETE);
        }
        else{
            duration = System.currentTimeMillis() - historicProcessInstance.getStartTime().getTime();
            processInfo.setStatus(ProcessStatusEnum.ACTIVE);
        }
        processInfo.setEndReason(historicProcessInstance.getDeleteReason());
        processInfo.setDuration(formatDuration(duration));
        processInfo.setAdmin(false);
        processInfo.setType(ModelTypeEnum.DEV);
        processInfo.setTenantId(historicProcessInstance.getTenantId());
        processInfo.setParentProcessId("");
        //TODO 剩余状态后续继续补充
        processViewerModel.setProcessInfo(processInfo);
    }


    /**
     * 时间格式化函数
     * @param durationInMnMillis 毫秒数
     * @return
     */
    public static String formatDuration(long durationInMnMillis){
        //转换成天
        long days = TimeUnit.MILLISECONDS.toDays(durationInMnMillis);
        //转换成小时
        long hours = TimeUnit.MILLISECONDS.toHours(durationInMnMillis) % 24 ;
        //转成分钟
        long minutes = TimeUnit.MINUTES.toMinutes(durationInMnMillis) % 60;
        //转成秒
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMnMillis) % 60;
        //装成毫秒
        long milliseconds = durationInMnMillis % 1000;

        StringBuilder result = new StringBuilder();
        if(days > 0){
            result.append(days).append("天 ");
        }

        if(hours > 0){
            result.append(hours).append("小时 ");
        }

        if(minutes > 0){
            result.append(minutes).append("分钟 ");
        }

        if(seconds > 0){
            result.append(seconds).append("秒 ");
        }

        if(milliseconds > 0){
            result.append(milliseconds).append("毫秒 ");
        }

        return result.toString();
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.PI_GET_INSTANCE_DEV_VIEWER.getId();
    }
}
