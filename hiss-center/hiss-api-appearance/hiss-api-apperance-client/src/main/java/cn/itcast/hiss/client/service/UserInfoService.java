package cn.itcast.hiss.client.service;

import cn.itcast.hiss.api.server.common.UserInfo;

import java.util.List;

/**
 * UserInfoService
 *
 * @author: wgl
 * @describe: 获取用户信息Service--需要用户自己实现
 * @date: 2022/12/28 10:10
 */
public interface UserInfoService {


    /**
     * 获取直属领导
     * @param palyload
     * @return
     */
    public List<String> getDirectLeader(UserInfo palyload);


    /**
     * 获得直属部门领导
     * @param palyload
     * @return
     */
    public List<String> getDirectDepartmentLeader(UserInfo palyload);



    /**
     * 获取指定角色
     * @param palyload
     * @return
     */
    public List<String> getDesignatedRole(UserInfo palyload);


    /**
     * 指定部门
     * @param palyload
     * @return
     */
    public List<String> getDesignateDepartment(UserInfo palyload);

    /**
     * 指定用户
     * @param userInfo
     * @return
     */
    List<String> getDirectUser(UserInfo userInfo);
}
