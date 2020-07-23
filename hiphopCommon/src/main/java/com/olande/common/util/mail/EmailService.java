package com.olande.common.util.mail;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author chengfr
 * @class EmailService.java
 * @date 2020-05-25 PM 04:13:15
 * @describe
 */
public final class EmailService {
    /**
     * 邮件配置属性文件基础名称
     */
    public final static String EMAIL_CONFIG_FILE_NAME = "emailconfig";

    /**
     * @param subject     :邮件主题
     * @param sendText    :邮件内容
     * @param receiveAdds :群发邮件地址列表
     * @throws Exception
     */
    public static final void sentMailText(String subject, String sendText,
                                          List<String> receiveAdds) throws Exception {
        ResourceBundle rb = ResourceBundle
                .getBundle(EMAIL_CONFIG_FILE_NAME);
        String host = rb.getString("mail.smtp.host");
        boolean auth = Boolean.valueOf(rb.getString("mail.smtp.auth"))
                .booleanValue();
        String username = rb.getString("mail.userName");
        String password = rb.getString("mail.passWord");
        String sendAddress = rb.getString("mail.sendAddress");
        Properties props = System.getProperties();
        // 发送邮件服务器地址
        props.put("mail.host", host);
        // 这里auth设置为true
        props.put("mail.smtp.auth", auth);
        // 发送邮件帐号
        EmailAuthenticator anthent = new EmailAuthenticator(username, password);
        Session mailSession = Session.getDefaultInstance(props, anthent);
        MimeMessage mymessage = new MimeMessage(mailSession);
        mymessage.setFrom(new InternetAddress(sendAddress));
        // 设置收件人地址
        int size = receiveAdds.size();
        for (int index = 0; index < size; index++) {
            mymessage.addRecipients(Message.RecipientType.TO,
                    getAddressByType(receiveAdds.get(index)));
        }
        mymessage.setSentDate(new java.util.Date());
        // 设置主题
        mymessage.setSubject(subject);
        // 设置内容
        mymessage.setText(sendText);
        // 保存设置
        mymessage.saveChanges();
        // 发送
        Transport.send(mymessage);
    }

    /**
     * @param subject    :主题
     * @param sendText   :邮件内容
     * @param receiveAdd :接收邮件地址
     * @throws Exception
     */
    public static final void sentMailText(String subject, String sendText,
                                          String receiveAdd) throws Exception {
        ResourceBundle rb = ResourceBundle
                .getBundle(EMAIL_CONFIG_FILE_NAME);
        String host = rb.getString("mail.smtp.host");
        boolean auth = Boolean.valueOf(rb.getString("mail.smtp.auth"))
                .booleanValue();
        String username = rb.getString("mail.userName");
        String password = rb.getString("mail.passWord");
        String sendAddress = rb.getString("mail.sendAddress");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        EmailAuthenticator anthent = new EmailAuthenticator(username, password);
        Session mailSession = Session.getDefaultInstance(props, anthent);
        MimeMessage mymessage = new MimeMessage(mailSession);
        mymessage.setFrom(new InternetAddress(sendAddress));
        mymessage.addRecipients(Message.RecipientType.TO,
                getAddressByType(receiveAdd));
        mymessage.setSentDate(new java.util.Date());
        mymessage.setSubject(subject);
        mymessage.setText(sendText);
        mymessage.saveChanges();
        Transport.send(mymessage);

    }

    /**
     * @param subject     :邮件主题
     * @param sendHtml    :发送邮件内容:html格式
     * @param receiveAdds :群发邮件地址列表
     * @throws Exception
     */
    public static final void sentMailHtml(String subject, String sendHtml,
                                          List<String> receiveAdds) throws Exception {
        ResourceBundle rb = ResourceBundle
                .getBundle(EMAIL_CONFIG_FILE_NAME);
        String host = rb.getString("mail.smtp.host");
        boolean auth = Boolean.valueOf(rb.getString("mail.smtp.auth"))
                .booleanValue();
        String username = rb.getString("mail.userName");
        String password = rb.getString("mail.passWord");
        String sendAddress = rb.getString("mail.sendAddress");
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        EmailAuthenticator anthent = new EmailAuthenticator(username, password);
        Session mailSession = Session.getDefaultInstance(props, anthent);
        MimeMessage mymessage = new MimeMessage(mailSession);
        mymessage.setFrom(new InternetAddress(sendAddress));
        int size = receiveAdds.size();
        for (int index = 0; index < size; index++) {
            mymessage.addRecipients(Message.RecipientType.TO,
                    getAddressByType(receiveAdds.get(index)));
        }
        mymessage.setSentDate(new java.util.Date());
        mymessage.setSubject(subject);
        mymessage.setDataHandler(new DataHandler(new ByteArrayDataSource(
                sendHtml, "text/html")));
        mymessage.saveChanges();
        Transport.send(mymessage);
    }

    /**
     * 邮箱发送验证码服务
     * @param subject    :邮件主题
     * @param sendHtml   :发送邮件内容:html格式
     * @param receiveAdd ：接收邮件地址
     * @throws Exception
     */
    public static final void sentMailHtml(String subject, String sendHtml,
                                          String receiveAdd) throws Exception {
        ResourceBundle rb = ResourceBundle
                .getBundle(EMAIL_CONFIG_FILE_NAME);//获取邮箱配置文件
        String host = rb.getString("mail.smtp.host");//获取邮箱服务地址IP和端口
        boolean auth = Boolean.valueOf(rb.getString("mail.smtp.auth"))//获取邮箱服务是否被授权
                .booleanValue();
        String username = rb.getString("mail.userName");//邮箱用户名
        String password = rb.getString("mail.passWord");//邮箱密码
        String sendAddress = rb.getString("mail.sendAddress");//邮箱地址
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth);
        EmailAuthenticator anthent = new EmailAuthenticator(username, password);//模拟邮箱登录，验证邮件服务
        Session mailSession = Session.getDefaultInstance(props, anthent);
        MimeMessage mymessage = new MimeMessage(mailSession);
        mymessage.setFrom(new InternetAddress(sendAddress));//发送者
        mymessage.addRecipients(Message.RecipientType.TO,
                getAddressByType(receiveAdd));//注册用户的邮箱账户
        mymessage.setSentDate(new java.util.Date());
        mymessage.setSubject(subject, "utf-8");
        mymessage.setDataHandler(new DataHandler(new ByteArrayDataSource(
                sendHtml, "text/html")));
        mymessage.saveChanges();
        Transport.send(mymessage);
    }

    protected static InternetAddress[] getAddressByType(String address)
            throws Exception {
        if (!(address == null || address.trim().equals(""))) {
            String[] addressList = address.split(",");
            InternetAddress addr[] = new InternetAddress[addressList.length];
            for (int i = 0; i < addressList.length; i++) {
                if (addressList[i] == null || addressList[i].trim().equals("")) {
                    continue;
                } else {
                    addr[i] = new InternetAddress(addressList[i]);
                }
            }
            return addr;
        } else {
            return null;
        }
    }
}
