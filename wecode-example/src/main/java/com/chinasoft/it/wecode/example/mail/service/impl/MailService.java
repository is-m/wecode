package com.chinasoft.it.wecode.example.mail.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.function.Function;

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
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SentDateTerm;
import javax.mail.search.SubjectTerm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.chinasoft.it.wecode.example.mail.config.MailConfig;
import com.chinasoft.it.wecode.example.mail.dto.MailDto;
import com.chinasoft.it.wecode.example.mail.dto.MailQueryDto;
import com.chinasoft.it.wecode.example.mail.dto.MailResponseDto;
import com.chinasoft.it.wecode.example.mail.service.IMailService;

/**
 * 邮件服务
 * 
 * @author Administrator
 *
 */
@Service
public class MailService implements IMailService {

	private static final Logger log = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private MailConfig mailConfig;

	/**
	 * 
	 * 构造函数,初始化一个MimeMessage对象
	 * 
	 */

	public class ShowMail {

		private MimeMessage mimeMessage = null;

		private String saveAttachPath = ""; // 附件下载后的存放目录

		private StringBuffer bodyText = new StringBuffer(); // 存放邮件内容的StringBuffer对象

		public ShowMail() {

		}

		public ShowMail(MimeMessage mimeMessage) {
			this.mimeMessage = mimeMessage;
		}

		/**
		 * 
		 * * 获得发件人的地址和姓名
		 * 
		 */
		public String getFrom() throws Exception {

			InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();

			String from = address[0].getAddress();

			if (from == null) {
				from = "";
			}

			String personal = address[0].getPersonal();
			if (personal == null) {
				personal = "";
				System.out.println("无法知道发送者的姓名.");
			}

			String fromAddr = null;
			if (personal != null || from != null) {
				fromAddr = personal + "<" + from + ">";
				System.out.println("发送者是：" + fromAddr);
			}

			return fromAddr;
		}

		/**
		 * 
		 * * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同
		 * 
		 * * "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
		 * 
		 */
		public String getMailAddress(Message.RecipientType type) throws Exception {
			String mailAddr = "";
			InternetAddress[] address = (InternetAddress[]) mimeMessage.getRecipients(type);
			if (address != null) {
				for (int i = 0; i < address.length; i++) {
					String emailAddr = address[i].getAddress();
					if (emailAddr == null) {
						emailAddr = "";
					} else {
						System.out.println("转换之前的emailAddr: " + emailAddr);
						emailAddr = MimeUtility.decodeText(emailAddr);
						System.out.println("转换之后的emailAddr: " + emailAddr);
					}

					String personal = address[i].getPersonal();
					if (personal == null) {
						personal = "";
					} else {
						System.out.println("转换之前的personal: " + personal);
						personal = MimeUtility.decodeText(personal);
						System.out.println("转换之后的personal: " + personal);
					}

					String compositeto = personal + "<" + emailAddr + ">";
					System.out.println("完整的邮件地址：" + compositeto);
					mailAddr += "," + compositeto;
				}

				mailAddr = mailAddr.substring(1);

			}

			return mailAddr;
		}

		/**
		 * 
		 * * 获得邮件主题
		 * 
		 */

		public String getSubject() throws MessagingException {

			String subject = "";

			try {

				System.out.println("转换前的subject：" + mimeMessage.getSubject());

				subject = MimeUtility.decodeText(mimeMessage.getSubject());

				System.out.println("转换后的subject: " + mimeMessage.getSubject());

				if (subject == null) {

					subject = "";

				}

			} catch (Exception exce) {

				exce.printStackTrace();

			}

			return subject;

		}

		/**
		 * 
		 * * 获得邮件发送日期
		 * 
		 */

		public Date getSentDate() throws Exception {
			return mimeMessage.getSentDate();
		}

		/**
		 * 
		 * * 获得邮件正文内容
		 * 
		 */

		public String getBodyText() {
			return bodyText.toString();
		}

		/**
		 * 
		 * * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件
		 * 
		 * * 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
		 * 
		 */

		public void getMailContent(Part part) throws Exception {

			String contentType = part.getContentType();

			// 获得邮件的MimeType类型

			System.out.println("邮件的MimeType类型: " + contentType);

			int nameIndex = contentType.indexOf("name");

			boolean conName = false;

			if (nameIndex != -1) {

				conName = true;

			}

			System.out.println("邮件内容的类型:　" + contentType);

			if (part.isMimeType("text/plain") && conName == false) {

				// text/plain 类型

				bodyText.append((String) part.getContent());

			} else if (part.isMimeType("text/html") && conName == false) {

				// text/html 类型

				bodyText.append((String) part.getContent());

			} else if (part.isMimeType("multipart/*")) {

				// multipart/*

				Multipart multipart = (Multipart) part.getContent();

				int counts = multipart.getCount();

				for (int i = 0; i < counts; i++) {

					getMailContent(multipart.getBodyPart(i));

				}

			} else if (part.isMimeType("message/rfc822")) {

				// message/rfc822

				getMailContent((Part) part.getContent());

			} else {

			}

		}

		/**
		 * 
		 * * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"
		 * 
		 */

		public boolean getReplySign() throws MessagingException {

			boolean replySign = false;

			String needReply[] = mimeMessage.getHeader("Disposition-Notification-To");

			if (needReply != null) {
				replySign = true;
			}

			if (replySign) {

				System.out.println("该邮件需要回复");

			} else {

				System.out.println("该邮件不需要回复");

			}

			return replySign;

		}

		/**
		 * 
		 * 获得此邮件的Message-ID
		 * 
		 */

		public String getMessageId() throws MessagingException {

			String messageID = mimeMessage.getMessageID();

			System.out.println("邮件ID: " + messageID);

			return messageID;

		}

		/**
		 * 
		 * 判断此邮件是否已读，如果未读返回false,反之返回true
		 * 
		 */
		public boolean isNew() throws MessagingException {

			boolean isNew = false;

			Flags flags = ((Message) mimeMessage).getFlags();

			Flags.Flag[] flag = flags.getSystemFlags();

			System.out.println("flags的长度:　" + flag.length);

			for (int i = 0; i < flag.length; i++) {

				if (flag[i] == Flags.Flag.SEEN) {

					isNew = true;

					System.out.println("seen email...");

					// break;

				}

			}

			return isNew;

		}

		/**
		 * 
		 * 判断此邮件是否包含附件
		 * 
		 */
		public boolean isContainAttach(Part part) throws Exception {
			boolean attachFlag = false;

			if (part.isMimeType("multipart/*")) {

				Multipart mp = (Multipart) part.getContent();

				for (int i = 0; i < mp.getCount(); i++) {

					BodyPart mPart = mp.getBodyPart(i);

					String disposition = mPart.getDisposition();

					if (disposition != null
							&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
						attachFlag = true;
					else if (mPart.isMimeType("multipart/*")) {
						attachFlag = isContainAttach((Part) mPart);
					} else {
						String conType = mPart.getContentType();
						if (conType.toLowerCase().indexOf("application") != -1)
							attachFlag = true;
						if (conType.toLowerCase().indexOf("name") != -1)
							attachFlag = true;
					}

				}

			} else if (part.isMimeType("message/rfc822")) {
				attachFlag = isContainAttach((Part) part.getContent());
			}

			return attachFlag;

		}

		/**
		 * 
		 * * 保存附件
		 * 
		 */
		public void saveAttachment(Part part) throws Exception {

			String fileName = "";

			if (part.isMimeType("multipart/*")) {
				Multipart mp = (Multipart) part.getContent();
				for (int i = 0; i < mp.getCount(); i++) {
					BodyPart mPart = mp.getBodyPart(i);
					String disposition = mPart.getDisposition();
					if (disposition != null
							&& ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
						fileName = mPart.getFileName();
						if (fileName.toLowerCase().indexOf("gb2312") != -1) {
							fileName = MimeUtility.decodeText(fileName);
						}
						saveFile(fileName, mPart.getInputStream());
					} else if (mPart.isMimeType("multipart/*")) {
						saveAttachment(mPart);
					} else {
						fileName = mPart.getFileName();
						if (fileName != null && (fileName.toLowerCase().indexOf("GB2312") != -1)) {

							fileName = MimeUtility.decodeText(fileName);

							saveFile(fileName, mPart.getInputStream());
						}

					}

				}

			} else if (part.isMimeType("message/rfc822")) {

				saveAttachment((Part) part.getContent());

			}

		}

		/**
		 * 
		 * 设置附件存放路径
		 * 
		 */

		public void setAttachPath(String attachPath) {
			this.saveAttachPath = attachPath;
		}

		/**
		 * 
		 * * 获得附件存放路径
		 * 
		 */
		public String getAttachPath() {
			return saveAttachPath;
		}

		/**
		 * 
		 * * 真正的保存附件到指定目录里
		 * 
		 */
		private void saveFile(String fileName, InputStream in) throws Exception {

			String osName = System.getProperty("os.name");

			String storeDir = getAttachPath();

			String separator = "";

			if (osName == null) {
				osName = "";
			}

			if (osName.toLowerCase().indexOf("win") != -1) {

				separator = "\\";

				if (storeDir == null || storeDir.equals(""))

					storeDir = "c:\\tmp";

			} else {

				separator = "/";

				storeDir = "/tmp";

			}

			File storeFile = new File(storeDir + separator + fileName);

			System.out.println("附件的保存地址:　" + storeFile.toString());

			BufferedOutputStream bos = null;
			BufferedInputStream bis = null;

			try {
				bos = new BufferedOutputStream(new FileOutputStream(storeFile));
				bis = new BufferedInputStream(in);
				int c;
				while ((c = bis.read()) != -1) {
					bos.write(c);
					bos.flush();
				}
			} catch (Exception exception) {
				throw new Exception("文件保存失败!");
			} finally {
				bos.close();
				bis.close();
			}

		}
	}

	@Override
	public void read(MailQueryDto mailQueryDto, Function<MailDto, MailResponseDto> consumer) {
		// https://blog.csdn.net/siscoyeoh/article/details/49227423
		// https://blog.csdn.net/qq_26797239/article/details/80729767
		// https://blog.csdn.net/siscoyeoh/article/details/49227423
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		session.setDebug(mailConfig.isDebug());

		String user = null;
		String password = null;

		if (StringUtils.isEmpty(mailConfig.getPop3User())) {
			user = mailConfig.getAccount();
			password = mailConfig.getPassword();
		} else {
			user = mailConfig.getPop3User();
			password = mailConfig.getPop3Password();
		}

		Store store = null;
		Folder folder = null;

		try {
			store = session.getStore("pop3");

			log.debug("connect pop3 host {} with user {}", mailConfig.getPop3Host(), user);

			store.connect(mailConfig.getPop3Host(), user, password);

			folder = store.getFolder("INBOX");
			folder.open(Folder.READ_WRITE);

			int messageCount = folder.getMessageCount();
			log.info("user inbox mail count is {}", messageCount);

			// 设置查询条件
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, -1);
			Date mondayDate = calendar.getTime();
			// 日期小于一天内的
			SearchTerm comparisonTermLe = new SentDateTerm(ComparisonTerm.GT, mondayDate);
			// 主题包含test的
			SearchTerm address = new SubjectTerm("test");
			// 创建条件组合
			SearchTerm comparisonAndTerm = new AndTerm(address, comparisonTermLe);

			// 查询邮件
			Message[] matcheds = folder.search(comparisonAndTerm);

			log.info("search mail where time greate [{}] and subjectTerm is [{}] count {}", mondayDate,
					address.toString(), matcheds.length);

			for (int i = 0; i < matcheds.length; i++) {
				ShowMail re = new ShowMail((MimeMessage) matcheds[i]);

				System.out.println("邮件　" + i + "　主题:　" + re.getSubject());

				System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());

				System.out.println("邮件　" + i + "　是否需要回复:　" + re.getReplySign());

				System.out.println("邮件　" + i + "　是否已读:　" + re.isNew());

				System.out.println("邮件　" + i + "　是否包含附件:　" + re.isContainAttach((Part) matcheds[i]));

				System.out.println("邮件　" + i + "　发送人地址:　" + re.getFrom());

				System.out.println("邮件　" + i + "　收信人地址:　" + re.getMailAddress(Message.RecipientType.TO));

				System.out.println("邮件　" + i + "　抄送:　" + re.getMailAddress(Message.RecipientType.CC));

				System.out.println("邮件　" + i + "　暗抄:　" + re.getMailAddress(Message.RecipientType.BCC));

				System.out.println("邮件　" + i + "　发送时间:　" + re.getSentDate());

				System.out.println("邮件　" + i + "　邮件ID:　" + re.getMessageId());

				re.getMailContent((Part) matcheds[i]);

				System.out.println("邮件　" + i + "　正文内容:　\r\n" + re.getBodyText());

				re.setAttachPath("d:\\mailtemp");

				// re.saveAttachMent((Part) message[i]);
			}

		} catch (Exception e) {
			log.error("inbox read fail", e);
		} finally {
			if (folder != null) {
				try {
					folder.close(true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
			if (store != null) {
				try {
					store.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void send(MailDto mailDto) throws UnsupportedEncodingException {
		// https://zhidao.baidu.com/question/75127798.html
		String user = null;
		String password = null;

		if (StringUtils.isEmpty(mailConfig.getSmtpUser())) {
			user = mailConfig.getAccount();
			password = mailConfig.getPassword();
		} else {
			user = mailConfig.getSmtpUser();
			password = mailConfig.getSmtpPassword();
		}

		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", mailConfig.getSmtpHost());
			props.put("mail.smtp.port", mailConfig.getSmtpPort());
			props.put("mail.smtp.from", "liaofeng@bf-201904271430.com");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(mailConfig.isDebug());

			Transport transport = session.getTransport("smtp");
			transport.connect(mailConfig.getSmtpHost(), "liaofeng@bf-201904271430.com", password);

			log.info("build mail message");
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(user, "liaofeng@bf-201904271430.com", "UTF-8");
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress("liaoxianmu@bf-201904271430.com");
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject("send test mail " + System.currentTimeMillis(), "UTF-8");
			msg.setText("test mail", "UTF-8");
			msg.saveChanges();
			transport.sendMessage(msg, msg.getAllRecipients());
			log.info("mail send completed.");
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
