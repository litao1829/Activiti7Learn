package cn.itcast.hiss.client.handler.custom;

import cn.hutool.core.util.ObjectUtil;
import cn.itcast.hiss.api.client.common.MethodType;
import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.client.manager.HissVariableHandlerManager;
import cn.itcast.hiss.cmd.handler.CmdHandler;
import cn.itcast.hiss.handler.HandlerIdClientEnum;
import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.sender.common.VariableMethodMessage;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CustomVariablesMethodHandler
 *
 * @author: wgl
 * @describe: 变量方法handler
 * @date: 2022/12/28 10:10
 */
@Component
@Slf4j
public class CustomVariablesMethodHandler implements CmdHandler<VariableMethodMessage> {

    @Override
    public void invoke(Message params, MessageContext messageContext) {
        Object palyload = params.getPalyload();
        VariableMethod variableMethod = null;
        if( palyload instanceof JSONObject){
            JSONObject temp = (JSONObject) palyload;
            variableMethod = temp.toJavaObject(VariableMethod.class);
        }else {
            variableMethod = (VariableMethod) params.getPalyload();
        }
        //根据参数获取对应的坐标
        //拿到对应的get、query、three方法
        //判断当前对应的请求类型
        String variableType = variableMethod.getVariableType();
        MethodType methodType = HissVariableHandlerManager.getMethodTypeByType(variableType);
        List<VariablesResultDTO> variablesResultDTO = null;
        switch (variableMethod.getType()) {
            case "get":
                VariablesGetDTO variablesGetDTO = new VariablesGetDTO();
                BeanUtils.copyProperties(palyload,variablesGetDTO);
                variablesResultDTO = methodType.get(variablesGetDTO);
                break;
            case "query":
                VariablesQueryDTO variablesQueryDTO = new VariablesQueryDTO();
                BeanUtils.copyProperties(palyload,variablesQueryDTO);
                variablesResultDTO = methodType.query(variablesQueryDTO);
                break;
            case "tree":
                VariablesTreeDTO variablesTreeDTO = new VariablesTreeDTO();
                BeanUtils.copyProperties(palyload,variablesTreeDTO);
                variablesResultDTO = methodType.tree(variablesTreeDTO);
                break;
        }
        if (ObjectUtil.isNotNull(variableType)) {
            List<VariablesResultDTO> finalVariablesResultDTO = variablesResultDTO;
            log.info("客户端获取的结果为：{}",finalVariablesResultDTO);
            messageContext.setResult(new ConcurrentHashMap<>() {
                {
                    put("flag", finalVariablesResultDTO);
                }
            });
        }
    }

    @Override
    public String getId() {
        return HandlerIdClientEnum.CUSTOM_VARIABLES_METHOD.getId();
    }
}
