package cn.itcast.hiss.process.activiti.properties;

import lombok.Data;

/*
 * @author miukoo
 * @description 邮件服务器配置类
 * @date 2023/5/29 19:59
 * @version 1.0
 **/
@Data
public class MailServerInfo {
    protected String mailServerHost;
    protected int mailServerPort;
    protected String mailServerUsername;
    protected String mailServerPassword;
    protected boolean mailServerUseSSL;
    protected boolean mailServerUseTLS;
    protected String defaultEncoding="UTF-8";
}
