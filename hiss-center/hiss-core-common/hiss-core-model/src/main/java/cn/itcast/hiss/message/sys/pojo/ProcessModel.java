package cn.itcast.hiss.message.sys.pojo;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import cn.itcast.hiss.common.enums.ModelTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
* <p>
* 流程设计
* </p>
*
* @author lenovo
* @since 2023-06-25 08:48:47
*/
@Getter
@Setter
public class ProcessModel extends PageRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设计名称
     */
    private String id;

    /**
    * 设计名称
    */
    private String name;

    /**
     * 分类id
     */
    private String category;

    /**
    * 所属应用
    */
    private String userAppId;

    /**
     * 图标
     */
    private String icon;

    /**
     * 发布ID
     */
    private String deploymentId;

    /**
     * 描述
     */
    private String description;

    /**
     * 类型
     */
    private ModelTypeEnum type = ModelTypeEnum.BIS;

    /**
     * 超级管理员的ID
     */
    private String adminId;


}
