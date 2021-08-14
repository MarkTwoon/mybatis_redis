package com.chinasoft.mybatis_redis.util;

import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendMailUtil {

	//private static String userEmail="1196129830@qq.com";
	/*电子邮件的 发送过程方法*/
	public static  void sendEmail(String userEmail, Map<String,Object> map) throws  Exception{
		/* 实例化一个  邮箱服务器对象*/
		Properties  prop=new Properties();
		/*开启发送邮件的BeDug模式   万一发生异常 可以查看报错*/
		prop.setProperty("mail.debug", "true");
		/*设置邮箱服务器主机名*/
		prop.setProperty("mail.host", "smtp.qq.com");
		/*因为邮件审查机制 所以发送邮件服务器   需要身份验证*/
		prop.setProperty("mail.smtp.auth", "true");
		/*设置发送邮件协议名称*/
		prop.setProperty("mail.transport.protocol", "smtp");
		/*开启SSL加密 如果不加密 邮件内容泄露  * 则邮件无法发送*/
		MailSSLSocketFactory sf=new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);
		/*创建Session对象*/
		Session session=Session.getInstance(prop);
		/*通过session对象  得到发送邮箱的服务器子对象*/
		Transport ts=session.getTransport();
		/*链接邮箱服务器 并设置邮箱类型 邮箱发送账户 及邮箱授权码   jusczoryduvwbgic */
		ts.connect("smtp.qq.com", "154593566", "remrmfzfvlvvbiaa");
		/*创建邮件对象*/
		Message message=createSimpleMail(session,userEmail,map);
		/*发送邮件 并结束*/
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
	}

	/*继续编辑  邮件的 发送内容*/
	public static MimeMessage createSimpleMail(Session session,String userEmail, Map<String,Object> map) throws Exception{
		/*创建邮件对象*/
		MimeMessage message=new MimeMessage(session);
		/*指明邮件的 发件人*/
		message.setFrom(new InternetAddress("154593566@qq.com"));
		/*指明邮件的收件人  传递的参数userEmail*/
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
		/*编辑邮件的标题*/
		message.setSubject("美好时光相册网");
		/*邮件的文本内容  注意是可以在邮件中写H5对应标签的*/
		message.setContent("<h2  style='color:red;'>您已经上传<br><br>"+map.get("imgName")+"相片成功！，祝您休闲愉快</h2><img src='"+map.get("imgUrl")+"'>大吉大利!!!", "text/html;charset=UTF-8");
		return message;
	}



	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			sendEmail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}

