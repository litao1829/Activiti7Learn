package cn.itcast.hiss.api.client.task;

import lombok.Data;

/**
 * 知会
 *
 * @author: miukoo
 * @describe: 知会
 * @date: 2022/12/28 10:10
 */
@Data
public class NotificationTask {

    /**
     * 知会、抄送、知晓
     */
    private String operatorName="";

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 知会的人ID
     */
    private String userId;

    /**
     * 知会的人名称
     */
    private String userName;

}
