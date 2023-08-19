package cn.itcast.hiss.process.activiti.service;

/*
 * @author miukoo
 * @description 对客户端用户的信息进行查询或者缓存
 * @date 2023/7/5 20:19
 * @version 1.0
 **/
public interface ClientUserService {

    /**
     * 依据用户id 换取用户名称
     * @param tenantId
     * @param userId
     * @return
     */
    public String getUserName(String tenantId,String userId);

    /**
     * 直接缓存一个用户信息
     * @param tenantId
     * @param userId
     * @param userName
     * @return
     */
    public void cacheUserName(String tenantId, String userId, String userName);

}
