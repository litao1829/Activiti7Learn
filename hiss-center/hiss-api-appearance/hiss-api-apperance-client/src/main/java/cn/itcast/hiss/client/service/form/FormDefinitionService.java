package cn.itcast.hiss.client.service.form;

import cn.itcast.hiss.api.client.form.FormDefinition;

/**
 * FormDefinition
 *
 * @author: wgl
 * @describe: 流程定义业务层
 * @date: 2022/12/28 10:10
 */
public interface FormDefinitionService {

    /**
     * 获取表单定义数据
     * @param ids
     * @param query
     * @return
     */
    public FormDefinition getFormDefinition(String ids,String query);

}