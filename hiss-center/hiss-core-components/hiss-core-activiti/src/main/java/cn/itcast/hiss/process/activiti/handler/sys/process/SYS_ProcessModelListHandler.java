package cn.itcast.hiss.process.activiti.handler.sys.process;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.PageResponseResult;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessModelMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.process.activiti.mapper.ActReModelMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.ActReModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 *
 * @author: miukoo
 * @describe: 流程设计查询处理器
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_ProcessModelListHandler implements CmdHandler<ProcessModelMessage> {

    @Autowired
    private HissUserAppMapper hissUserAppMapper;
    @Autowired
    private ActReModelMapper actReModelMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessModel processModel = (ProcessModel) params.getPalyload();

//        ModelQuery modelQuery = repositoryService.createModelQuery();
        LambdaQueryWrapper<ActReModel> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ActReModel::getKey,ModelTypeEnum.BIS.name());
        wrapper.eq(ActReModel::getTenantId,processModel.getUserAppId());
        wrapper.eq(ActReModel::getCategory,processModel.getCategory());
        boolean canQuery = isAdmin(params);
        if(!canQuery){
            List<String> userAppIdsList = hissUserAppMapper.getUserAppIdsList(params.getMessageAuth().getCurrentUser().getUserId());
            canQuery = userAppIdsList.contains(processModel.getUserAppId());
        }
        if(!canQuery){
            messageContext.addError("msg","无权限操作");
            return;
        }
        if(StrUtil.isNotEmpty(processModel.getName())){
            wrapper.like(ActReModel::getName,processModel.getName());
        }
        processModel.checkParam();
        Page<ActReModel> page = Page.of(processModel.getCurrent(),processModel.getPageSize());
        page = actReModelMapper.selectPage(page,wrapper);
        long total = page.getTotal();
        PageResponseResult pageResponseResult = new PageResponseResult(processModel.getCurrent(),processModel.getPageSize(),total);
        // 查询分页数据
        pageResponseResult.setData(page.getRecords());
        messageContext.addResultAndCount("result", pageResponseResult);
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_PROCESS_MODEL_LIST.getId();
    }
}
