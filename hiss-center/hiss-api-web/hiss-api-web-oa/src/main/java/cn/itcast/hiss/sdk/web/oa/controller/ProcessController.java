package cn.itcast.hiss.sdk.web.oa.controller;

import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.message.client.perporties.MessageTcpSenderProperties;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.sdk.web.oa.dto.ProcessApplayDto;
import cn.itcast.hiss.sdk.web.oa.dto.ResponseResult;
import cn.itcast.hiss.sdk.web.oa.service.ProcessService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description：用户前端控制器
 */
@Slf4j
@RestController
@RequestMapping("process")
public class ProcessController {

    @Autowired
    ProcessService processService;

    @Autowired
    MessageTcpSenderProperties messageTcpSenderProperties;

    /**
     * 流程分类列表
     * @return
     */
    @PostMapping("/list/category")
    public ResponseResult listProcessCategory() {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        List<ProcessCategory> list = processService.listProcessCategory(tenantId);
        return ResponseResult.success(list);
    }

    /**
     * 表单分类列表
     * @return
     */
    @PostMapping("/form/category")
    public ResponseResult listFormCategory() {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        List<ProcessCategory> list = processService.listFormCategory(tenantId);
        return ResponseResult.success(list);
    }

    /**
     * 分类流程
     * @param categoryId
     * @return
     */
    @PostMapping("/list/category/process/{categoryId}")
    public ResponseResult listCategoryProcess(@PathVariable("categoryId") String categoryId) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        List<ProcessModel> list = processService.listCategoryProcess(tenantId, categoryId);
        return ResponseResult.success(list);
    }

    /**
     * 删除流程实例
     * @param id
     * @return
     */
    @PostMapping("/instance/delete/{id}")
    public ResponseResult deleteProcessInstance(@PathVariable("id") String id) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.deleteProcessInstance(tenantId, id);
        return ResponseResult.success(list);
    }

    /**
     * 删除表单实例
     * @param id
     * @return
     */
    @PostMapping("/form/delete/{id}")
    public ResponseResult deleteFormModel(@PathVariable("id") String id) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.deleteFormModel(tenantId, id);
        return ResponseResult.success(list);
    }


    /**
     * 流程实例列表
     * @return
     */
    @PostMapping("/list/instance")
    public ResponseResult listProcessInstance(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listProcessInstance(tenantId, dto);
        return ResponseResult.success(list);
    }

    /**
     * 流程实例列表
     * @return
     */
    @PostMapping("/form/list")
    public ResponseResult listForm(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listForm(tenantId, dto);
        return ResponseResult.success(list);
    }

    /**
     * 业务流程列表
     * @return
     */
    @PostMapping("/list/bis")
    public ResponseResult listProcessBis(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listCategoryProcess(tenantId, dto, ModelTypeEnum.BIS);
        return ResponseResult.success(list);
    }

    /**
     * 开发流程列表
     * @return
     */
    @PostMapping("/list/dev")
    public ResponseResult listProcessDev(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listCategoryProcess(tenantId, dto, ModelTypeEnum.DEV);
        return ResponseResult.success(list);
    }

    /**
     * 删除申请
     * @param id
     * @return
     */
    @PostMapping("/applay/delete/{id}")
    public ResponseResult deleteApplayProcess(@PathVariable("id") String id) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.deleteApplayProcess(tenantId,id);
        return ResponseResult.success(list);
    }

    /**
     * 删除流程模型
     * @param id
     * @return
     */
    @PostMapping("/model/delete/{id}")
    public ResponseResult deleteProcessModel(@PathVariable("id") String id) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.deleteProcessModel(tenantId,id);
        return ResponseResult.success(list);
    }

    /**
     * 发起流程
     * @param id
     * @return
     */
    @PostMapping("/start/{id}")
    public ResponseResult startProcess(@PathVariable("id") String id) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        String processInstanceId = processService.startProcess(tenantId, id);
        return ResponseResult.success(processInstanceId);
    }

    /**
     * 我的申请列表
     * @param dto
     * @return
     */
    @PostMapping("/applay/list")
    public ResponseResult listApplayProcess(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listApplayProcess(tenantId,dto);
        return ResponseResult.success(list);
    }

    /**
     * 我的办理列表
     * @param dto
     * @return
     */
    @PostMapping("/handle/list")
    public ResponseResult listHandleProcess(@RequestBody ProcessApplayDto dto) {
        String tenantId = messageTcpSenderProperties.getSources().keySet().iterator().next();
        JSONObject list = processService.listHandleProcess(tenantId,dto);
        return ResponseResult.success(list);
    }
}
