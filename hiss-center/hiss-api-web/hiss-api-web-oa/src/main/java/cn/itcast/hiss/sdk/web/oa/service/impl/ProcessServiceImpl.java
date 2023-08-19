package cn.itcast.hiss.sdk.web.oa.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.client.template.HissClientApperanceTemplate;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.common.enums.ProcessStatusEnum;
import cn.itcast.hiss.message.CurrentUser;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessInstance;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.sdk.web.oa.dto.ProcessApplayDto;
import cn.itcast.hiss.sdk.web.oa.exception.HissException;
import cn.itcast.hiss.sdk.web.oa.service.CurrentUserService;
import cn.itcast.hiss.sdk.web.oa.service.ProcessService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author miukoo
 * @description 对接流程中心
 * @date 2023/6/30 14:51
 * @version 1.0
 **/
@Service
public class ProcessServiceImpl implements ProcessService {

    @Autowired
    HissClientApperanceTemplate hissClientApperanceTemplate;

    @Autowired
    CurrentUserService currentUserService;

    @Override
    public List<ProcessCategory> listProcessCategory(String tenantId) {
        ProcessCategory query = new ProcessCategory();
        query.setUserAppId(tenantId);
        MessageContext messageContext = hissClientApperanceTemplate.listProcessCategory(tenantId, query, getCurrentUser());
        if(messageContext.isSuccess()){
            Object result = messageContext.getResult().get("result");
            if(result instanceof com.alibaba.fastjson2.JSONObject){
                com.alibaba.fastjson2.JSONObject j2 = (com.alibaba.fastjson2.JSONObject)result;
                com.alibaba.fastjson2.JSONArray data = j2.getJSONArray("data");
                return data.toJavaList(ProcessCategory.class);
            }else{
                JSONObject j1 = (JSONObject) messageContext.getResult().get("result");
                JSONArray data = j1.getJSONArray("data");
                List<ProcessCategory> list = toJavaList(data,ProcessCategory.class);
                return list;
            }
        }else{
            parseError(messageContext);
        }
        return new ArrayList<>();
    }

    @Override
    public List<ProcessCategory> listFormCategory(String tenantId) {
        ProcessCategory query = new ProcessCategory();
        query.setUserAppId(tenantId);
        MessageContext messageContext = hissClientApperanceTemplate.listProcessCategory(tenantId, query, getCurrentUser());
        if(messageContext.isSuccess()){
            Object result = messageContext.getResult().get("result");
            if(result instanceof com.alibaba.fastjson2.JSONObject){
                com.alibaba.fastjson2.JSONObject j2 = (com.alibaba.fastjson2.JSONObject)result;
                com.alibaba.fastjson2.JSONArray data = j2.getJSONArray("data");
                return data.toJavaList(ProcessCategory.class);
            }else{
                JSONObject j1 = (JSONObject) messageContext.getResult().get("result");
                JSONArray data = j1.getJSONArray("data");
                List<ProcessCategory> list = toJavaList(data,ProcessCategory.class);
                return list;
            }
        }else{
            parseError(messageContext);
        }
        return new ArrayList<>();
    }

    private void parseError(MessageContext messageContext){
        ConcurrentHashMap<String, Object> error = messageContext.getError();
        if(error.containsKey("msg")){
            throw new HissException((String) messageContext.getError().get("msg"));
        }
    }

    @Override
    public List<ProcessModel> listCategoryProcess(String tenantId, String category){
        ProcessModel query = new ProcessModel();
        query.setUserAppId(tenantId);
        query.setCategory(category);
        MessageContext messageContext = hissClientApperanceTemplate.listCategoryProcess(tenantId, query, getCurrentUser());
        if(messageContext.isSuccess()){
            Object result = messageContext.getResult().get("result");
            if(result instanceof com.alibaba.fastjson2.JSONObject){
                com.alibaba.fastjson2.JSONObject j2 = (com.alibaba.fastjson2.JSONObject)result;
                com.alibaba.fastjson2.JSONArray data = j2.getJSONArray("data");
                return data.toJavaList(ProcessModel.class);
            }else{
                JSONObject j1 = (JSONObject) messageContext.getResult().get("result");
                JSONArray data = j1.getJSONArray("data");
                return toJavaList(data,ProcessModel.class);
            }
        }else{
            parseError(messageContext);
        }
        return new ArrayList<>();
    }

    @Override
    public JSONObject listApplayProcess(String tenantId, ProcessApplayDto dto) {
        ProcessInstance query = new ProcessInstance();
        BeanUtils.copyProperties(dto,query);
        if(StrUtil.isNotEmpty(dto.getStatus())) {
            query.setStatus(ProcessStatusEnum.valueOf(dto.getStatus()));
        }
        query.setCurrent(dto.getPageNum());
        query.setPageSize(dto.getPageSize());
        MessageContext messageContext = hissClientApperanceTemplate.listApplayProcess(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public String startProcess(String tenantId, String id) {
        ProcessDesignModel query = new ProcessDesignModel();
        query.setModelId(id);
        MessageContext messageContext = hissClientApperanceTemplate.startBisPreProcess(tenantId, query, getCurrentUser());
        if(messageContext.isSuccess()){
            String preProcessInstanceId = ""+messageContext.getResult().get("preProcessInstanceId");
            return preProcessInstanceId;
        }else{
            parseError(messageContext);
        }
        return null;
    }

    @Override
    public JSONObject listHandleProcess(String tenantId, ProcessApplayDto dto) {
        ProcessInstance query = new ProcessInstance();
        BeanUtils.copyProperties(dto,query);
        if(StrUtil.isNotEmpty(dto.getStatus())) {
            query.setStatus(ProcessStatusEnum.valueOf(dto.getStatus()));
        }
        query.setCurrent(dto.getPageNum());
        query.setPageSize(dto.getPageSize());
        MessageContext messageContext = hissClientApperanceTemplate.listHandleProcess(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    /**
     * 删除个人申请的流程
     * @param tenantId
     * @param id
     * @return
     */
    @Override
    public JSONObject deleteApplayProcess(String tenantId, String id) {
        ProcessInstance query = new ProcessInstance();
        query.setId(id);
        MessageContext messageContext = hissClientApperanceTemplate.deleteApplayProcess(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject listCategoryProcess(String tenantId, ProcessApplayDto dto, ModelTypeEnum type) {
        ProcessModel query = new ProcessModel();
        query.setUserAppId(tenantId);
        query.setType(type);
        query.setName(dto.getName());
        query.setCategory(dto.getCategory());
        MessageContext messageContext = hissClientApperanceTemplate.listCategoryProcess(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject deleteProcessModel(String tenantId, String id) {
        ProcessModel query = new ProcessModel();
        query.setId(id);
        query.setAdminId(currentUserService.getAdminId());
        MessageContext messageContext = hissClientApperanceTemplate.deleteProcess(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject listProcessInstance(String tenantId, ProcessApplayDto dto) {
        ProcessInstance query = new ProcessInstance();
        BeanUtils.copyProperties(dto,query);
        if(StrUtil.isNotEmpty(dto.getStatus())) {
            query.setStatus(ProcessStatusEnum.valueOf(dto.getStatus()));
        }
        query.setTenantId(tenantId);
        query.setAdminId(currentUserService.getAdminId());
        query.setCurrent(dto.getPageNum());
        query.setPageSize(dto.getPageSize());
        MessageContext messageContext = hissClientApperanceTemplate.listProcessInstance(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject listForm(String tenantId, ProcessApplayDto dto) {
        ProcessModel query = new ProcessModel();
        query.setUserAppId(tenantId);
        query.setAdminId(currentUserService.getAdminId());
        query.setName(dto.getName());
        query.setCategory(dto.getCategory());
        query.setCurrent(dto.getPageNum());
        query.setPageSize(dto.getPageSize());
        MessageContext messageContext = hissClientApperanceTemplate.listFormModel(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject deleteProcessInstance(String tenantId, String id) {
        ProcessInstance query = new ProcessInstance();
        query.setId(id);
        query.setTenantId(tenantId);
        query.setAdminId(currentUserService.getAdminId());
        MessageContext messageContext = hissClientApperanceTemplate.deleteProcessInstance(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    @Override
    public JSONObject deleteFormModel(String tenantId, String id) {
        ProcessModel query = new ProcessModel();
        query.setId(id);
        query.setAdminId(currentUserService.getAdminId());
        MessageContext messageContext = hissClientApperanceTemplate.deleteFormModel(tenantId, query, getCurrentUser());
        return parseMessageContext(messageContext);
    }

    /**
     * 解析对象结果
     * @param messageContext
     * @return
     */
    private JSONObject parseMessageContext( MessageContext messageContext ){
        if(messageContext.isSuccess()){
            Object result = messageContext.getResult().get("result");
            if(result instanceof com.alibaba.fastjson2.JSONObject){
                com.alibaba.fastjson2.JSONObject j2 = (com.alibaba.fastjson2.JSONObject)result;
                return JSON.parseObject(JSON.toJSONString(j2));
            }else{
                return (JSONObject)result;
            }
        }else{
            parseError(messageContext);
        }
        return new JSONObject();
    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    private CurrentUser getCurrentUser(){
        return currentUserService.getCurrentUser();
    }

    private <T> List<T> toJavaList(Object object,Class<T> clazz){
        String json = JSON.toJSONString(object);
        return JSON.parseArray(json,clazz);
    }
}
