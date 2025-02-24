package cn.itcast.hiss.process.activiti.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.api.client.processdefinition.ProcessDesignModel;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.process.activiti.mapper.ActReModelMapper;
import cn.itcast.hiss.process.activiti.pojo.ActReModel;
import cn.itcast.hiss.process.activiti.service.ActivitiDesignerService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务模式的实现
 * @author miukoo
 * @description
 * @date 2023/6/6 10:28
 * @version 1.0
 **/
@Service
@Transactional
public class ActivitiDesignerServiceImpl implements ActivitiDesignerService {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    ActReModelMapper actReModelMapper;



    public void saveDev(Message message, MessageContext messageContext) {
        ProcessDesignModel processDesignModel = (ProcessDesignModel) message.getPalyload();
        Model model = null;
        if (StrUtil.isNotEmpty(processDesignModel.getModelId())) {
            model = repositoryService.getModel(processDesignModel.getModelId());
        }
        if (model == null) {
            model = repositoryService.newModel();
            model.setKey(ModelTypeEnum.DEV.name());
        }
        if (model != null) {
            if (StrUtil.isNotEmpty(processDesignModel.getName())) {
                model.setName(processDesignModel.getName());
            }
            if (StrUtil.isNotEmpty(processDesignModel.getBusinessKey())) {
                model.setKey(processDesignModel.getBusinessKey());
            }
            if (StrUtil.isNotEmpty(processDesignModel.getCategory())) {
                model.setCategory(processDesignModel.getCategory());
            }
            model.setTenantId(message.getMessageAuth().getTenant());
            repositoryService.saveModel(model);// 保存基本信息
            repositoryService.addModelEditorSource(model.getId(), processDesignModel.getContent().getBytes());// 保存源码信息
            messageContext.addResult("modelId", model.getId());
        }
    }

    /**
     * 获取内容
     * @param message
     * @param messageContext
     */
    public void get(Message message, MessageContext messageContext) {
        ProcessDesignModel processDesignModel = (ProcessDesignModel) message.getPalyload();
        ActReModel model = null;
        if (StrUtil.isNotEmpty(processDesignModel.getModelId())) {
            model = actReModelMapper.selectById(processDesignModel.getModelId());
            if (model != null) {
                processDesignModel.setIcon(model.getIcon());
                processDesignModel.setDescription(model.getDescription());
                processDesignModel.setCategory(model.getCategory());
                processDesignModel.setType(ModelTypeEnum.valueOf(model.getKey()));
                processDesignModel.setName(model.getName());
                processDesignModel.setContent(new String(repositoryService.getModelEditorSource(model.getId())));
            }
        }
        messageContext.addResult("model", processDesignModel);
    }

}
