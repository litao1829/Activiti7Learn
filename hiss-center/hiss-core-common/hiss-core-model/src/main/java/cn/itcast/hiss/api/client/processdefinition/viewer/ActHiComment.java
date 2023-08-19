package cn.itcast.hiss.api.client.processdefinition.viewer;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
* <p>
* act_hi_comment 实体类
* </p>
*
* @author miukoo
* @since 2023-06-05 11:48:39
*/
@Data
public class ActHiComment implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id= UUID.randomUUID().toString();
    private String action="AddComment";
    private String fullMsg;
    private String message;
    private String procInstId;
    private String taskId;
    private Date time = new Date();
    private String type="comment";
    private String userId;

}
