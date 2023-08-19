package cn.itcast.hiss.message.http.sender.service.impl;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.http.sender.properties.HttpSenderSource;
import cn.itcast.hiss.message.http.sender.properties.MessageHttpSenderProperties;
import cn.itcast.hiss.message.http.sender.service.HttpAuthService;
import cn.itcast.hiss.message.http.sender.service.HttpSenderService;
import cn.itcast.hiss.message.sender.Sender;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/*
 * @author miukoo
 * @description //TODO
 * @date 2023/5/13 16:03
 * @version 1.0
 **/
public class HttpSenderServiceImpl implements HttpSenderService {

    @Autowired
    RestTemplate httpRestTemplate;
    @Autowired
    HttpAuthService httpAuthService;
    @Autowired
    MessageHttpSenderProperties messageHttpSenderProperties;

    @Override
    public void send(Sender sender,MessageContext messageContext, Message message) {
        String tenant = message.getMessageAuth().getTenant();
        Map<String, HttpSenderSource> sources = messageHttpSenderProperties.getSources();
        HttpSenderSource httpSenderSource = sources.get(tenant);
        Assert.notNull(httpSenderSource,"未找到租户"+tenant+"的授权配置");
        HttpHeaders headers = new HttpHeaders();
        httpAuthService.authCreate(headers,message,httpSenderSource);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(message),headers);
        ResponseEntity<HashMap> response = httpRestTemplate.postForEntity(httpSenderSource.getUrl(), requestEntity, HashMap.class);
        messageContext.addResult(sender.typeName(),response);
    }

}
