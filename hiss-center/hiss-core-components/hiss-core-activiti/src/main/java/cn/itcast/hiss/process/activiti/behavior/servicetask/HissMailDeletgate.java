package cn.itcast.hiss.process.activiti.behavior.servicetask;

import cn.itcast.hiss.common.enums.EventOperationTypeEnum;
import cn.itcast.hiss.event.pojo.HissActivitiEvent;
import cn.itcast.hiss.process.activiti.listener.HissActivitiEventBuilder;
import cn.itcast.hiss.process.activiti.properties.MailServerInfo;
import cn.itcast.hiss.process.activiti.properties.MailServerInfoProperties;
import cn.itcast.hiss.process.activiti.util.RemoteExecuteClassUtil;
import cn.itcast.hiss.server.template.HissServerApperanceTemplate;
import lombok.Data;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.bpmn.behavior.MailActivityBehavior;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;

import javax.activation.DataSource;
import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/*
 * @author miukoo
 * @description 邮件的发送，支持推送给客户端发送，和服务器发送两种配置
 *  1、没有配置，则推送到客户端执行
 *  2、有配置则服务器帮发送
 * @date 2023/5/29 19:53
 * @version 1.0
 **/
@Data
public class HissMailDeletgate extends MailActivityBehavior {

    MailServerInfoProperties mailServerInfoProperties;
    HissServerApperanceTemplate hissServerApperanceTemplate;
    RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution execution) {
        boolean doIgnoreException = Boolean.parseBoolean(getStringFromField(ignoreException, execution));
        String exceptionVariable = getStringFromField(exceptionVariableName, execution);
        Email email = null;
        try {
            String toStr = getStringFromField(to, execution);
            String fromStr = getStringFromField(from, execution);
            String ccStr = getStringFromField(cc, execution);
            String bccStr = getStringFromField(bcc, execution);
            String subjectStr = getStringFromField(subject, execution);
            String textStr = textVar == null ? getStringFromField(text, execution) : getStringFromField(getExpression(execution, textVar), execution);
            String htmlStr = htmlVar == null ? getStringFromField(html, execution) : getStringFromField(getExpression(execution, htmlVar), execution);
            String charSetStr = getStringFromField(charset, execution);
            List<File> files = new LinkedList<File>();
            List<DataSource> dataSources = new LinkedList<DataSource>();
            try {
                Method method = MailActivityBehavior.class.getDeclaredMethod("getFilesFromFields", Expression.class, DelegateExecution.class, List.class, List.class);
                method.invoke(this,attachments, execution, files, dataSources);
            } catch (Exception e) {
            }

            email = createEmail(textStr, htmlStr, false);
            addTo(email, toStr);
            setFrom(email, fromStr, execution.getTenantId());
            addCc(email, ccStr);
            addBcc(email, bccStr);
            setSubject(email, subjectStr);
            MailServerInfo mailServerInfo = setMailServerInfoProperties(email, execution.getTenantId());
            setCharset(email, charSetStr);
            attach(email, files, dataSources);
            if(mailServerInfo!=null) {
                email.send();// 服务器代发送
            }else{
                HissActivitiEvent hissActivitiEvent = HissActivitiEventBuilder.builder().execution(execution).build();
                hissActivitiEvent.setOperationType(EventOperationTypeEnum.SEND_MAIL_EVENT_NOTICE);
                hissActivitiEvent.setEventData(email);
                //  回调客户端发送
                RemoteExecuteClassUtil.notifyClient(hissServerApperanceTemplate,runtimeService,execution.getTenantId(),hissActivitiEvent,execution,false);
            }
        } catch (ActivitiException e) {
            handleException(execution, e.getMessage(), e, doIgnoreException, exceptionVariable);
        } catch (EmailException e) {
            handleException(execution, "Could not send e-mail in execution " + execution.getId(), e, doIgnoreException, exceptionVariable);
        }
        leave(execution);
    }


    protected MailServerInfo setMailServerInfoProperties(Email email, String tenantId) {
        MailServerInfo mailServerInfo = mailServerInfoProperties.getServers().get(tenantId);
        if(mailServerInfo!=null){
            email.setHostName(mailServerInfo.getMailServerHost());
            email.setSmtpPort(mailServerInfo.getMailServerPort());
            email.setSSLOnConnect(mailServerInfo.isMailServerUseSSL());
            email.setStartTLSEnabled(mailServerInfo.isMailServerUseTLS());
            email.setCharset(mailServerInfo.getDefaultEncoding());
            String user = mailServerInfo.getMailServerUsername();
            String password = mailServerInfo.getMailServerPassword();
            if (user != null && password != null) {
                email.setAuthentication(user, password);
            }
        }
        return mailServerInfo;
    }
    public Expression getCc() {
        return cc;
    }

    public void setCc(Expression cc) {
        this.cc = cc;
    }

    public Expression getTo() {
        return to;
    }

    public void setTo(Expression to) {
        this.to = to;
    }

    public Expression getFrom() {
        return from;
    }

    public void setFrom(Expression from) {
        this.from = from;
    }

    public Expression getBcc() {
        return bcc;
    }

    public void setBcc(Expression bcc) {
        this.bcc = bcc;
    }

    public Expression getSubject() {
        return subject;
    }

    public void setSubject(Expression subject) {
        this.subject = subject;
    }

    public Expression getText() {
        return text;
    }

    public void setText(Expression text) {
        this.text = text;
    }

    public Expression getTextVar() {
        return textVar;
    }

    public void setTextVar(Expression textVar) {
        this.textVar = textVar;
    }

    public Expression getHtml() {
        return html;
    }

    public void setHtml(Expression html) {
        this.html = html;
    }

    public Expression getHtmlVar() {
        return htmlVar;
    }

    public void setHtmlVar(Expression htmlVar) {
        this.htmlVar = htmlVar;
    }

    public Expression getCharset() {
        return charset;
    }

    public void setCharset(Expression charset) {
        this.charset = charset;
    }

    public Expression getIgnoreException() {
        return ignoreException;
    }

    public void setIgnoreException(Expression ignoreException) {
        this.ignoreException = ignoreException;
    }

    public Expression getExceptionVariableName() {
        return exceptionVariableName;
    }

    public void setExceptionVariableName(Expression exceptionVariableName) {
        this.exceptionVariableName = exceptionVariableName;
    }

    public Expression getAttachments() {
        return attachments;
    }

    public void setAttachments(Expression attachments) {
        this.attachments = attachments;
    }
}
