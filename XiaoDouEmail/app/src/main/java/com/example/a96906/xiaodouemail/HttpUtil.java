package com.example.a96906.xiaodouemail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * Created by 96906 on 2016/11/18.
 */

public class HttpUtil {
    //连接邮箱
    public Session Login(){
        //连接服务器
        Session session = IsLoginRight(MyApplication.info);
        return session;
    }

    /**
     * 登录
     */
    private Session IsLoginRight(MailInfo info){
        //判断是否要登入验证
        MyAuthenticator authenticator = null;
        if(info.isValidate()){
            //创建一个密码验证器
            authenticator = new MyAuthenticator(info.getUserName(),info.getPassword());
        }
        //根据邮件会话属性和密码验证器构建一个发送邮件的Session
        Session sendMailSession = Session.getDefaultInstance(info.getProperties(),authenticator);
        try{
            Transport transport = sendMailSession.getTransport("smtp");
            transport.connect(info.getMailServerHost(),info.getUserName(),info.getPassword());
        }catch (MessagingException e){
            e.printStackTrace();
            return null;
        }
        return sendMailSession;
    }

    /**
     * 发送邮件的信息及格式
     */
    public boolean sendTextMail(MailInfo mailInfo,Session sendMailSession){
        //判断是否需要身份验证
        try{
            //创建一个邮件消息
            Message mailMessage = new MimeMessage(sendMailSession);
            //创建发送者地址
            Address address = new InternetAddress(mailInfo.getFromAddress());
            //设置邮件的发送者
            mailMessage.setFrom(address);
            //创建邮件的接收者地址，并设置到邮件消息中
            Address[] tos = null;
            String[] receivers = mailInfo.getReceivers();
            if(receivers!=null){
                //为每一个邮件接收者创建地址
                tos = new InternetAddress[receivers.length];
                for(int i=0;i<receivers.length;i++){
                    tos[i] = new InternetAddress(receivers[i]);
                }
            }else {
                return false;
            }
            //接收者类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO,tos);
            //设置邮件主题
            mailMessage.setSubject(mailInfo.getTheme());
            //设置发送时间
            mailMessage.setSentDate(new Date());
            //设置邮件消息的主要类容
            String mailContent = mailInfo.getTextContent();

            //存储多个Bodypart对象
            Multipart mm = new MimeMultipart();
            //设置文本内容
            BodyPart mdp = new MimeBodyPart();
            //设置内容/格式/编码方式
            mdp.setContent(mailContent,"text/html;charset=gb2312");
            //加入MimeMultipart中
            mm.addBodyPart(mdp);

            Attachment attachment;
            FileDataSource fds;
            List<Attachment> list = mailInfo.getAttachmentInfos();
            for(int i=0;i<list.size();i++){
                attachment = list.get(i);
                fds = new FileDataSource(attachment.getFilePath());
                mdp = new MimeBodyPart();
                mdp.setDataHandler(new DataHandler(fds));
                try{
                    mdp.setFileName(MimeUtility.encodeText(fds.getName()));
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                mm.addBodyPart(mdp);
            }
            mailMessage.setContent(mm);
            mailMessage.saveChanges();

            // 设置邮件支持多种格式
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);
            Transport.send(mailMessage); //发送邮件
            return true;
        }catch (MessagingException e){
            e.printStackTrace();
        }
        return false;
    }
}
