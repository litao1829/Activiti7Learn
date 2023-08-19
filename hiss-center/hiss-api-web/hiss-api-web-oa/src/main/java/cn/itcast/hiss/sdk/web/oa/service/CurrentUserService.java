package cn.itcast.hiss.sdk.web.oa.service;

import cn.itcast.hiss.message.CurrentUser;

/*
 * @author miukoo
 * @description 获取当前登录人接口
 * @date 2023/7/12 10:43
 * @version 1.0
 **/
public interface CurrentUserService {
    public CurrentUser getCurrentUser();

    /**
     * 获取超级管理员的ID，在业务系统中删除某些数据时会使用
     * 改ID是流程中心中的用户ID
     * @return
     */
    default String getAdminId(){
        return "";
    }
}
