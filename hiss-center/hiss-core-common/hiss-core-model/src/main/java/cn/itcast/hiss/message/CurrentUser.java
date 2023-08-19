package cn.itcast.hiss.message;

import lombok.Data;

import java.util.List;

/**
 * CurrentUser
 *
 * @author: wgl
 * @describe: 当前操作用户
 * @date: 2022/12/28 10:10
 */
@Data
public class CurrentUser {

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 候选组,多个用逗号分隔
     */
    private String userGroups;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 岗位Id
     */
    private String postId;

    /**
     * 岗位名
     */
    private String postName;


}
