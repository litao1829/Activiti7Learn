package cn.itcast.hiss.api.server.common;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * CreateUser
 *
 * @author: wgl
 * @describe: 创建人
 * @date: 2022/12/28 10:10
 */
@Data
public class CreateUser {

    /**
     * 流程创建人id
     */
    private String createUserId;

    /**
     * 流程创建人姓名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date createTime;
}
