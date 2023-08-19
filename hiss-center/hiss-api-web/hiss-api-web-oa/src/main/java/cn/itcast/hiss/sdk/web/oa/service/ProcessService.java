package cn.itcast.hiss.sdk.web.oa.service;

import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessInstance;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.sdk.web.oa.dto.ProcessApplayDto;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/*
 * @author miukoo
 * @description 对接流程中心
 * @date 2023/6/30 14:47
 * @version 1.0
 **/
public interface ProcessService {

    /**
     * 获取全部的流程分类
     * @return
     */
    List<ProcessCategory> listProcessCategory(String tenantId);

    /**
     * 获取分类下的流程
     * @return
     */
    List<ProcessModel> listCategoryProcess(String tenantId, String category);

    JSONObject listApplayProcess(String tenantId, ProcessApplayDto dto);

    String startProcess(String tenantId, String id);

    JSONObject listHandleProcess(String tenantId, ProcessApplayDto dto);

    JSONObject deleteApplayProcess(String tenantId, String id);

    JSONObject listCategoryProcess(String tenantId, ProcessApplayDto dto, ModelTypeEnum type);

    JSONObject deleteProcessModel(String tenantId, String id);

    JSONObject listProcessInstance(String tenantId, ProcessApplayDto dto);

    JSONObject listForm(String tenantId, ProcessApplayDto dto);

    JSONObject deleteProcessInstance(String tenantId, String id);

    JSONObject deleteFormModel(String tenantId, String id);

    List<ProcessCategory> listFormCategory(String tenantId);
}
