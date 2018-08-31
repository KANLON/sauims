package com.fekpal.common.utils.msg.email;

import org.apache.commons.mail.*;
import org.springframework.mail.javamail.JavaMailSender;
import java.util.List;

/**
 * 邮件发送工具
 */
public class EmailSender {

    /**
     * 简单文本邮件，支持换行
     */
    private SimpleEmail simpleEmail;

    /**
     * 邮件发送
     */
    private JavaMailSender mailSender;

    /**
     * 发送人
     */
    private String defaultFrom;

    /**
     * 发送邮件
     *
     * @param subject 主题
     * @param msg     信息
     * @param to      接收人
     * @throws EmailException 邮件异常
     */
    @Deprecated
    public void sendMsg(String subject, String msg, String to) throws EmailException {

        if (simpleEmail == null) {
            throw new EmailException("simpleEmail can not be null");
        }
        simpleEmail.addTo(to);
        simpleEmail.setSubject(subject);
        simpleEmail.setMsg(msg);
        simpleEmail.send();
    }


    /**
     * 发送单个接收人邮件
     *
     * @param msg 信息
     * @throws EmailException 邮件异常
     */
    @Deprecated
    public void sendMsg(EmailMsg msg) throws EmailException {
        if (simpleEmail == null) {
            throw new EmailException("simpleEmail can not be null");
        }
        simpleEmail.addTo(msg.getTo());
        simpleEmail.setSubject(msg.getSubject());
        simpleEmail.setMsg(msg.getText());
        simpleEmail.send();
    }

    /**
     * 邮件发送
     *
     * @param msg 邮件信息封装
     */
    public void send(EmailMsg msg) {
        msg.setFrom(defaultFrom);
        mailSender.send(msg);
    }

    /**
     * 发送邮件
     *
     * @param subject 主题
     * @param msg     信息
     * @param recList 群发列表
     * @throws EmailException 邮件异常
     */
    @Deprecated
    public void sendMsg(String subject, String msg, List<String> recList) throws EmailException {
        if (simpleEmail == null) {
            throw new EmailException("simpleEmail can not be null");
        }
        String[] list = recList.toArray(new String[recList.size()]);
        simpleEmail.buildMimeMessage();
        simpleEmail.addTo(list);
        simpleEmail.setSubject(subject);
        simpleEmail.setMsg(msg);
        simpleEmail.send();
    }

    public String getDefaultFrom() {
        return defaultFrom;
    }

    public void setDefaultFrom(String defaultFrom) {
        this.defaultFrom = defaultFrom;
    }

    public SimpleEmail getSimpleEmail() {
        return simpleEmail;
    }

    public void setSimpleEmail(SimpleEmail simpleEmail) {
        this.simpleEmail = simpleEmail;
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
