package cn.itcast.hiss.process.activiti.handler.sys.scaffold;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.common.dtos.PageResponseResult;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sys.pojo.ProcessModel;
import cn.itcast.hiss.process.activiti.dto.scaffold.ScaffoldDto;
import cn.itcast.hiss.process.activiti.dto.scaffold.ScaffoldMessage;
import cn.itcast.hiss.process.activiti.mapper.ActReModelMapper;
import cn.itcast.hiss.process.activiti.mapper.HissUserAppMapper;
import cn.itcast.hiss.process.activiti.pojo.ActReModel;
import cn.itcast.hiss.process.activiti.properties.ScaffoldInfo;
import cn.itcast.hiss.process.activiti.properties.ScaffoldProperties;
import cn.itcast.hiss.process.activiti.util.RuoyiUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 *
 * @author: miukoo
 * @describe: 脚手架导出功能
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class SYS_ScaffoldExportHandler implements CmdHandler<ScaffoldMessage> {

    @Autowired
    private ScaffoldProperties scaffoldProperties;

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        ScaffoldDto scaffoldDto = (ScaffoldDto) params.getPalyload();
        Map<String, ScaffoldInfo> systems = scaffoldProperties.getSystems();
        if(systems!=null){
            ScaffoldInfo scaffoldInfo = systems.get(scaffoldDto.getTemplateName());
            if(scaffoldInfo!=null) {
                File file = RuoyiUtil.exportRuoyi(scaffoldDto, new File(scaffoldInfo.getTemplatePath()), new File(scaffoldInfo.getTempPath()));
                messageContext.addResultAndCount("download",file);
                messageContext.addResultAndCount("downloadName",scaffoldDto.getVersion()+".zip");
            }else{
                messageContext.addError("msg","没有找到服务端配置，不能导出");
            }
        }else{
            messageContext.addError("msg","没有找到服务端配置，不能导出");
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.SYS_SCAFFOLD_EXPORT.getId();
    }
}
