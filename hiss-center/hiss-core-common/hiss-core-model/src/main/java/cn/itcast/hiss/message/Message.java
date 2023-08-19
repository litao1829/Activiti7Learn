package cn.itcast.hiss.message;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.TypeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 可以用在消息发送和接收之间的消息超类
 * @author miukoo
 */
public interface Message<T> {
    // 默认是不授权的通信消息
    MessageAuth DEFAULT_AUTH_TYPE = new MessageAuth();

    /**
     * 消息的唯一标识符
     * @return
     */
    String getId();


    /**
     * 默认的授权类型
     * @return
     */
    default MessageAuth getMessageAuth(){
        return DEFAULT_AUTH_TYPE;
    }

    /**
     * 获取消息的内容
     * @return
     */
    T getPalyload();
    /**
     * 获取消息的配置，包含消息使用那些通道发送，使用异步还是同步
     */
    MessageConfig getMessageConfig();

    /**
     * 把这个消息转换成
     * @return
     */
    default void converterForm(Message kClass) throws Exception {
        Class thisClazz = getType(this.getClass());
        String className = "";
        if(kClass.getPalyload()!=null){
            className = kClass.getPalyload().getClass().getName();
        }else{
            Class srcClazz = getType(kClass.getClass());
            if(srcClazz!=null){
                className = srcClazz.getName();
            }
        }
        T kObject = null;
        if(className.equals(JSONObject.class.getName())){
            JSONObject jsonObject = (JSONObject) kClass.getPalyload();
            kObject = (T) jsonObject.toJavaObject(thisClazz);
        }
        if(className.equals(JSONArray.class.getName())){
            JSONArray jsonArray = (JSONArray) kClass.getPalyload();
            kObject = (T) jsonArray.toJavaObject(thisClazz);
        }
        if(className.equals(HashMap.class.getName())){
            HashMap hashMap = (HashMap) kClass.getPalyload();
            kObject = (T) JSON.parseObject(JSON.toJSONString(hashMap),thisClazz);
        }
        if(className.equals(LinkedHashMap.class.getName())){
            LinkedHashMap linkedHashMap = (LinkedHashMap) kClass.getPalyload();
            kObject = (T) JSON.parseObject(JSON.toJSONString(linkedHashMap),thisClazz);
        }
        BeanUtils.copyProperties(kClass,this);
        this.setPalyload(kObject);
    }

    void setPalyload(T kObject);


    /**
     * 获取一个类的泛型
     * @param clazz
     * @return
     * @throws ClassNotFoundException
     */
    default Class getType(Class clazz) throws ClassNotFoundException {
        Map<Type, Type> typeMap = TypeUtil.getTypeMap(clazz);
        Type typeClass = null;
        for (Type type : typeMap.keySet()) {
            typeClass = typeMap.get(type);
        }
        // 类的泛型
        if(typeClass==null){
            return null;
        }
        String className = typeClass.getTypeName();
        return Class.forName(className);
    }



    void setMessageAuth(MessageAuth messageAuth);

    void setId(String id);

    void setMessageConfig(MessageConfig messageConfig);
}
