package cn.itcast.hiss.api.server.common;

import lombok.Data;

/**
 * UserInfo
 *
 * @author: wgl
 * @describe: 历史节点的用户信息
 * @date: 2022/12/28 10:10
 */
@Data
public class UserInfo {

    /**
     * 上一个节点的执行人
     */
    private String lastAssigne;

    /**
     * 上一个节点的节点id
     */
    private String lastTaskId;


    /**
     * 上一个节点的节点名称
     */
    private String lastTaskName;

    /**
     * 当前节点的表达式
     */
    private String currentNodeVariable;


    /**
     * 流程的发起者
     */
    private CreateUser createUser;

    /**
     * 当前节点用户填写的节点值
     */
    private String currentNodeValue;

}
