package cn.itcast.hiss.process.activiti.service.impl;

import cn.itcast.hiss.api.client.HissProcessConstants;
import cn.itcast.hiss.api.client.common.VariableMethod;
import cn.itcast.hiss.api.client.dto.VariablesResultDTO;
import cn.itcast.hiss.process.activiti.service.ClientUserService;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * @author miukoo
 * @description 缓存在内存中的用户名称
 * @date 2023/7/5 20:31
 * @version 1.0
 **/
@Service
public class ClientUserServiceImpl implements ClientUserService {
    @Autowired
    private HissServerApperanceTemplate hissServerApperanceTemplate;

    Map<String, SoftReference<Map<String,String>>> MOMERY_CACHE = new HashMap<>();

    @Override
    public String getUserName(String tenantId, String userId) {
        SoftReference<Map<String, String>> data = MOMERY_CACHE.getOrDefault(tenantId, new SoftReference<>(new HashMap<>()));
        Map<String, String> map = data.get();
        if(map==null){
            data = new SoftReference<>(new HashMap<>());
        }
        String str = data.get().get(userId);
        if(str==null){
            // 重客户端去获取
            return queryClient(data,tenantId, userId);
        }
        return str;
    }

    @Override
    public void cacheUserName(String tenantId, String userId, String userName) {
        SoftReference<Map<String, String>> data = MOMERY_CACHE.getOrDefault(tenantId, new SoftReference<>(new HashMap<>()));
        Map<String, String> map = data.get();
        if(map==null){
            data = new SoftReference<>(new HashMap<>());
        }
        data.get().put(userId,userName);
        MOMERY_CACHE.put(tenantId,data);
    }

    /**
     * 从客户端查询
     * @param tenantId
     * @param userId
     * @return
     */
    private String queryClient(SoftReference<Map<String, String>> data,String tenantId,String userId){
        try {
            VariableMethod variableMethod = new VariableMethod();
            variableMethod.setIds(userId);
            variableMethod.setTenantId(tenantId);
            variableMethod.setType("get");
            variableMethod.setVariableType(HissProcessConstants.CLIENT_USER_NAME);
            List<Map> res = hissServerApperanceTemplate.getClientVariablesMethod(tenantId, variableMethod);
            if(res!=null){
                for (Map re : res) {
                    String name = (String) re.get("name");
                    data.get().put(userId,name);
                    MOMERY_CACHE.put(tenantId,data);
                    return name;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return userId;
    }

}
