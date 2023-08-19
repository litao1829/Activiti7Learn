package cn.itcast.hiss.message.http.receiver.controller;

import cn.hutool.core.util.StrUtil;
import cn.itcast.hiss.common.dtos.ResponseResult;
import cn.itcast.hiss.common.enums.AppHttpCodeEnum;
import cn.itcast.hiss.message.DefaultMessage;
import cn.itcast.hiss.message.MessageContext;
import cn.itcast.hiss.message.http.receiver.service.HttpAuthCheckService;
import cn.itcast.hiss.message.http.receiver.service.HttpReceiverService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ConcurrentHashMap;

/*
 * @author miukoo
 * @description 封装统一的http接收
 * @date 2023/5/13 21:46
 * @version 1.0
 **/
@CrossOrigin
@Controller
public class MessageHttpReceiverController {

    @Autowired
    private HttpReceiverService httpReceiverService;

    @PostMapping("/v1/receiver")
    public ResponseEntity receiver(@RequestBody DefaultMessage message,
                                   HttpServletRequest request, HttpServletResponse response) throws Exception {

        MessageContext context = httpReceiverService.receiver(message,request,response);
        if(context.isSuccess()){
            ConcurrentHashMap<String, Object> result = context.getResult();
            // 支持返回文件下载
            if(result.containsKey("download")){
                File download = (File) result.get("download");
                String fileName = (String) result.get("downloadName");
                if(StrUtil.isEmpty(fileName)){
                    fileName = download.getName();
                }
                FileInputStream fileInputStream = new FileInputStream(download);
                InputStreamResource inputStreamResource = new InputStreamResource(fileInputStream);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                httpHeaders.setContentDisposition(ContentDisposition.attachment().filename(URLEncoder.encode(fileName, "UTF-8")).build());
                httpHeaders.setContentLength(fileInputStream.available());
                return new ResponseEntity(inputStreamResource,httpHeaders, HttpStatus.OK);
            }else {
                return ResponseEntity.ok().body(ResponseResult.okResult(context.getResult()));
            }
        }else{
            if(context.getError().containsKey("msg")){
                return ResponseEntity.ok().body(ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, (String) context.getError().get("msg")));
            }
            return ResponseEntity.ok().body(ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, JSON.toJSONString(context.getError())));
        }
    }

}
