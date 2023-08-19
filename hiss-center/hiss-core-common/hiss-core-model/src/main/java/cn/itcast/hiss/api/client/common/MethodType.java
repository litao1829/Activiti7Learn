package cn.itcast.hiss.api.client.common;

import cn.itcast.hiss.api.client.dto.VariablesGetDTO;
import cn.itcast.hiss.api.client.dto.VariablesQueryDTO;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.api.client.dto.VariablesTreeDTO;
import cn.itcast.hiss.common.enums.InputType;
import lombok.Data;

import java.util.List;

/**
 * MethodType
 *
 * @author: wgl
 * @describe: 方法类型
 * @date: 2022/12/28 10:10
 */
@Data
public abstract class MethodType {

    /**
     * 设置成员属性
     */
    public String type = type();

    public String name = name();

    public String description = description();

    public InputType inputType = inputType();

    // 是否有实现
    protected boolean impl = false;

    /**
     * 对应的类型
     *
     * @return
     */
    public abstract String type();

    /**
     * 页面中名称
     *
     * @return
     */
    public abstract String name();


    /**
     * 类型描述
     * @return
     */
    public abstract String description();

    /**
     * 输入类型
     * @return
     */
    public abstract InputType inputType();

    /**
     * 任务类型
     *
     * @return
     */
    public abstract List<VariablesResultDTO> get(VariablesGetDTO getDTO);

    /**
     * 查询
     *
     * @param queryDTO
     * @return
     */
    public abstract List<VariablesResultDTO> query(VariablesQueryDTO queryDTO);

    /**
     * 树形查询
     *
     * @param treeDTO
     * @return
     */
    public abstract List<VariablesResultDTO> tree(VariablesTreeDTO treeDTO);
}
