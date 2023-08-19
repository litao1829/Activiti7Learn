package cn.itcast.hiss.process.activiti.handler.sys.fcategory;

import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.form.mapper.HissFormCategoryMapper;
import cn.itcast.hiss.form.pojo.HissFormCategory;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.ProcessCategoryMessage;
import cn.itcast.hiss.message.sys.pojo.ProcessCategory;
import cn.itcast.hiss.process.activiti.mapper.HissProcessCategoryMapper;
import cn.itcast.hiss.process.activiti.pojo.HissProcessCategory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 *
 * @author: miukoo
 * @describe: 流程分类列表
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_FormCategoryListHandler implements CmdHandler<ProcessCategoryMessage> {

    @Autowired
    private HissFormCategoryMapper hissFormCategoryMapper;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ProcessCategory processCategory = (ProcessCategory) params.getPalyload();
        LambdaQueryWrapper<HissFormCategory> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(HissFormCategory::getUserAppId,processCategory.getUserAppId());
        wrapper.orderByDesc(HissFormCategory::getOrd);
        messageContext.addResultAndCount("result", ResponseResult.okResult(hissFormCategoryMapper.selectList(wrapper)));
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_FORM_CATEGORY_LIST.getId();
    }
}
