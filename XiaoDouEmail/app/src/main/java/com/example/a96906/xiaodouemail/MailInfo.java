package com.example.a96906.xiaodouemail;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * 邮件的基本信息
 */

public class MailInfo implements Serializable{
    private static final long serialVersionUID = 1L; //保持版本兼容性
    //发邮件的服务器的IP和端口
    private String mailServerHost;
    private String mailServerPort = "465";
    //登录邮件发送服务器的用户名和密码
    private String userName;
    private String password;
    private boolean valiDate = false; //是否需要身份验证
    private String fromAddress; //邮件发送者地址
    private String Theme; //邮件主题
    private String textContent; //邮件文本内容
    private List<Attachment> attachmentInfos; //邮件附件的路径
    private String[] receivers; //邮件的接收者，可以有多个

    public void setMailServerHost(String mailServerHost) {
        this.mailServerHost = mailServerHost;
    }
    public String getMailServerHost() {
        return mailServerHost;
    }

    public void setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
    }
    public String getMailServerPort() {
        return mailServerPort;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public boolean isValidate() {
        return valiDate;
    }
    public void setValidate(boolean valiDate) {
        this.valiDate = valiDate;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
    public String getFromAddress() {
        return fromAddress;
    }

    public String getTheme() {
        return Theme;
    }
    public void setTheme(String theme) {
        this.Theme = theme;
    }

    public String getTextContent() {
        return textContent;
    }
    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public List<Attachment> getAttachmentInfos() {
        return attachmentInfos;
    }
    public void setAttachmentInfos(List<Attachment> attachmentInfos) {
        this.attachmentInfos = attachmentInfos;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }
    public String[] getReceivers() {
        return receivers;
    }





    /**
     * 获取邮件会话属性
     */
    public Properties getProperties(){
        Properties p = new Properties();
        p.put("mail.smtp.host",this.mailServerHost);
        p.put("mail.smtp.port",this.mailServerPort);
        p.put("mail.smtp.auth",valiDate ? "true" : "false");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.scoketFactory.port","465");

        return p;
    }
}

