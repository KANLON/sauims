package com.fekpal.tool;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Created by hasee on 2017/8/17.
 */
public class MailHtmlTool {

    //private FreeMarkerConfigurer freeMarkerConfigurer;
    private JavaMailSender mailSender;//spring配置中定义

    private SimpleMailMessage simpleMailMessage;//spring配置中定义

    private String from;
    private String to;
    private String subject;
    private String content;
    private String templateName;
    // 是否需要身份验证
    private boolean validate = false;



    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }

    /**
     * 发送模板邮件
     * @param recipient 发送人
     * @param subject 标题
     * @param content 内容正文，不可以包含html
     */
    public void sendWithTemplate(String recipient,String subject,String content) {
        mailSender = this.getMailSender();
        simpleMailMessage.setTo(recipient); //接收人
        simpleMailMessage.setFrom(simpleMailMessage.getFrom()); //发送人,从配置文件中取得
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(subject);
        mailSender.send(simpleMailMessage);
    }

    /**
     * 发送普通Html模板邮件
     * @param recipient 接受人
     * @param subject 标题
     * @param content 内容正文可以包含html
     * @throws MessagingException 错误
     */
    public  void  sendHtml(String recipient, String subject, String content) throws MessagingException{
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(recipient);
        messageHelper.setFrom(simpleMailMessage.getFrom());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        mailSender.send(mimeMessage);
    }

    /**
     * 发送普通Html模板邮件给多个人
     * @param recipients 接受人
     * @param subject 标题
     * @param content 内容正文可以包含html
     * @throws MessagingException 错误
     */
    public void sendHtmlToMany(List<String> recipients, String subject, String content) throws MessagingException{
        mailSender = this.getMailSender();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(recipients.toArray(new String[recipients.size()]));
        messageHelper.setFrom(simpleMailMessage.getFrom());
        messageHelper.setSubject(subject);
        messageHelper.setText(content, true);
        mailSender.send(mimeMessage);
    }

}
