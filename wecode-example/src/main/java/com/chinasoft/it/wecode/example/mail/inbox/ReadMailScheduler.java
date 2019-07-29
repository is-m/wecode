package com.chinasoft.it.wecode.example.mail.inbox;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SubjectTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
public class ReadMailScheduler {

	private static final Logger logger = LoggerFactory.getLogger(ReadMailScheduler.class);

	private static final String MULTI_PART = "multipart";

	private static final String APPLICATION_CONTEXT = "";

	private static final String MESSAGE_RFC = "";

	private static final String NAME_CONTEXT = "";

	private static volatile boolean runnable = false;

	@Scheduled(cron = "0/30 * * * * ? ")
	public void readInbox() {
		if(!runnable) {
			runnable = true;
		}else {
			return;
		}
		
		// 邮件配置信息
		String host = "pop.qq.com";
		String usr = "191625131@qq.com";
		// 非QQ密码，通过QQ邮箱获取,账号设置中获取
		String pwd = "ayqgfewtzltibijh";
		// 邮件配置类
		Properties props = new Properties();
		// props.setProperty("mail.store.protocol", "pop3"); // 协议
		props.setProperty("mail.pop3.port", "110"); // 端口
		props.setProperty("mail.pop3.host", "pop.qq.com"); // pop3服务器

		// SSL安全连接参数
		props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.pop3.socketFactory.fallback", "true");
		props.setProperty("mail.pop3.socketFactory.port", "995");

		// 邮件配置缓存
		Session session = Session.getDefaultInstance(props);

		session.setDebug(true);
		String fileName = null;
		try {
			// 邮件服务器的类型
			Store store = session.getStore("pop3");
			// 连接邮箱服务器
			store.connect(host, usr, pwd);
			// 获得用户的邮件帐户
			Folder folder = store.getFolder("INBOX");
			if (folder == null) {
				logger.info("获取邮箱文件信息为空");
			}
			// 设置对邮件帐户的访问权限可以读写
			folder.open(Folder.READ_WRITE);
			
			   // 获得收件箱  
	        //Folder folder = store.getFolder("INBOX");  
	        /* Folder.READ_ONLY：只读权限 
	         * Folder.READ_WRITE：可读可写（可以修改邮件的状态） 
	         */
	        //folder.open(Folder.READ_WRITE); //打开收件箱  

	        // 由于POP3协议无法获知邮件的状态,所以getUnreadMessageCount得到的是收件箱的邮件总数
	        // 获得收件箱中的邮件总数  
	        //System.out.println("邮件总数: " + folder.getMessageCount());   
	      
	        
			// https://blog.csdn.net/miaoy220/article/details/52413157           
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			Date mondayDate = calendar.getTime();
			SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.GT, mondayDate);
			SearchTerm address = new SubjectTerm("MU Report");
			SearchTerm comparisonAndTerm = new AndTerm(address, comparisonTermLe);
			Message[] messages = folder.search(comparisonAndTerm);
			for (int i = 0; i < messages.length; i++) {
				try {
					Thread.sleep(30000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
				
				MimeMessage msg = (MimeMessage) messages[i];
				// 判断是否有附件
				boolean isContainerAttachment = isContainAttachment(msg);
				if (isContainerAttachment) {
					// 保存附件
					fileName = saveAttachment(msg, "d:/app/temp");
					// 保存接收到的邮件并且收件箱删除邮件
					msg.setFlag(Flags.Flag.DELETED, true);
				}
				if (!isContainerAttachment) {
					continue;
				}
				
			}
			
			folder.close(true);
			store.close();
			// parseTxtService.readTxt(fileName);
		} catch (NoSuchProviderException e) {
			logger.error("接收邮箱信息异常:{}", e);
		} catch (MessagingException e) {
			logger.error("连接邮箱服务器信息异常:{}", e);
		} catch (IOException e) {
			logger.error("接收邮箱信息解析异常:{}", e); 
		} catch (IllegalStateException e) {
			logger.error("接收邮箱信息为空:{}", e);
		}finally {
			runnable = false;
		}
	}

	/**
	 * 判断邮件中是否包含附件
	 * 
	 * @return 邮件中存在附件返回true，不存在返回false
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean isContainAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType(MULTI_PART)) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType(MULTI_PART)) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf(APPLICATION_CONTEXT) != -1) {
						flag = true;
					}
					if (contentType.indexOf(MULTI_PART) != -1) {
						flag = true;
					}
				}
				if (flag) {
					break;
				}
			}
		} else if (part.isMimeType(MESSAGE_RFC)) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**
	 * 保存附件
	 * 
	 * @param part    邮件中多个组合体中的其中一个组合体
	 * @param destDir 附件保存目录
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String saveAttachment(Part part, String destDir)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		String fileName = null;
		if (part.isMimeType(MULTI_PART)) {
			Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
			// 复杂体邮件包含多个邮件体
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				// 获得复杂体邮件中其中一个邮件体
				BodyPart bodyPart = multipart.getBodyPart(i);
				// 某一个邮件体也有可能是由多个邮件体组成的复杂体
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					InputStream is = bodyPart.getInputStream();
					saveFile(is, destDir, decodeText(bodyPart.getFileName()));
					fileName = decodeText(bodyPart.getFileName());
				} else if (bodyPart.isMimeType(MULTI_PART)) {
					saveAttachment(bodyPart, destDir);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf(NAME_CONTEXT) != -1 || contentType.indexOf(APPLICATION_CONTEXT) != -1) {
						saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()));
						fileName = decodeText(bodyPart.getFileName());
					}
				}
			}
		} else if (part.isMimeType(MESSAGE_RFC)) {
			saveAttachment((Part) part.getContent(), destDir);
		}
		return fileName;
	}

	/**
	 * 读取输入流中的数据保存至指定目录
	 * 
	 * @param is       输入流
	 * @param fileName 文件名
	 * @param destDir  文件存储目录
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void saveFile(InputStream is, String destDir, String fileName) throws FileNotFoundException, IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		if (fileName.contains(".txt")) {
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destDir + fileName)));
			int len = -1;
			while ((len = bis.read()) != -1) {
				bos.write(len);
				bos.flush();
			}
			bos.close();
			bis.close();
		}
	}

	private String decodeText(String text) {
		return text;
	}

}
