package cn.itcast.hiss.message.sys.pojo;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

/*
 * @author miukoo
 * @date 2023/6/21 20:41
 * @version 1.0
 **/
@Data
public class SystemUser extends PageRequestDto {

    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    private List<String> createdTime;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    private Integer status;

    /**
     * 账户名
     */
    private String username;

    /**
     * 备注
     */
    private String note;

    /**
     * 关联的应用名称，逻辑字段
     */
    private String appName;
    /**
     * 创建时间，用于显示，逻辑字段
     */
    private Date cTime;

}
