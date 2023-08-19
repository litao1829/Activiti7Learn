package cn.itcast.hiss.message.sys.pojo;

import cn.itcast.hiss.common.dtos.PageRequestDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/*
 * @author miukoo
 * @date 2023/6/21 20:41
 * @version 1.0
 **/
@Data
public class UserApp extends PageRequestDto {

    private Long id;

    /**
     * 创建时间
     */
    private List<String> createdTime;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 网址
     */
    private String website;

    /**
     * 更新时间
     */
    private List<String> updatedTime;

    /**
     * 图标
     */
    private String icon;


}
