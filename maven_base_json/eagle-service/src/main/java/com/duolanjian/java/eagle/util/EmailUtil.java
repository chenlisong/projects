package com.duolanjian.java.eagle.util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.mail.util.MailSSLSocketFactory;

@Component
public class EmailUtil {
	
	@Autowired
	private Config config;

	private Log log = LogFactory.getLog(EmailUtil.class);
	
	public boolean sendEmail(String email, String title, String content) {
		
		boolean success = true;
		try{
			Properties props = new Properties();
	
			// 开启debug调试
			props.setProperty("mail.debug", "false");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.host", "smtp.qq.com");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
	
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			props.put("mail.smtp.ssl.enable", "true");
			props.put("mail.smtp.ssl.socketFactory", sf);
	
			Session session = Session.getInstance(props);
	
			// 邮件内容部分
			Message msg = new MimeMessage(session);
			msg.setSubject(title);
			msg.setText(content);
			// 邮件发送者
			msg.setFrom(new InternetAddress(config.emailAccount));
	
			// 发送邮件
			Transport transport = session.getTransport();
			transport.connect("smtp.qq.com", config.emailAccount, config.emailPassword);
	
			transport.sendMessage(msg, new Address[] { new InternetAddress(email) });
			transport.close();
			
			log.info("send email, host:"+email+", title:"+title+", content:"+content);
		} catch(Exception e) {
			success = false;
			log.error("exception:", e);
		}
		return success;
	}
	
}