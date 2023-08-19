package cn.itcast.hiss.message.http.receiver.sender;

import cn.itcast.hiss.message.Message;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.http.receiver.properties.HttpSenderSource;
import cn.itcast.hiss.message.http.receiver.properties.MessageHttpSenderProperties;
import cn.itcast.hiss.message.http.receiver.sender.service.HttpAuthService;
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

/**
 * MessageHttpSender
 *
 * @author: wgl
 * @describe: 消息发送器
 * @date: 2022/12/28 10:10
 */
public class ServerHttpSender extends Sender {

    @Autowired
    private MessageHttpSenderProperties messageHttpSenderProperties;

    @Autowired
    private HttpAuthService httpAuthService;

    @Autowired
    RestTemplate httpRestTemplate;

    @Override
    public void send(MessageContext messageContext, Message message) {
        String tenant = message.getMessageAuth().getTenant();
        Map<String, HttpSenderSource> sources = messageHttpSenderProperties.getSources();
        HttpSenderSource httpSenderSource = sources.get(tenant);
        Assert.notNull(httpSenderSource, "未找到租户" + tenant + "的授权配置");
        HttpHeaders headers = new HttpHeaders();
        httpAuthService.authCreate(headers, message, httpSenderSource);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(message), headers);
        ResponseEntity<HashMap> response = httpRestTemplate.postForEntity(httpSenderSource.getUrl(), requestEntity, HashMap.class);
        messageContext.addResult(typeName(), response);
    }

    @Override
    public String typeName() {
        return "http";
    }
}
