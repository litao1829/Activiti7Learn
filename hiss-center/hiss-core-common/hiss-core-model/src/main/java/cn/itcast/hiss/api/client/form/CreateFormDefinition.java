package cn.itcast.hiss.api.client.form;

import lombok.Data;

/**
 * CreateFormDefinition
 *
 * @author: wgl
 * @describe: 创建表单定义消息类
 * @date: 2022/12/28 10:10
 */
@Data
public class CreateFormDefinition {

    /**
     * 分组
     */
    private String category;

    /**
     * 流程定义id
     */
    private String id;

    /**
     * 流程定义名称
     */
    private String name;

    /**
     * 表单定义结构
     */
    private String version;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * 表单详情
     */
    private String formDetail;

    /**
     * 表单详情
     */
    private String userAppId;

}
