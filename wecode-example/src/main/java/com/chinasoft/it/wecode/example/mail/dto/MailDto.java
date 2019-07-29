package com.chinasoft.it.wecode.example.mail.dto;

import java.util.Date;

/**
 * 邮件对象
 * 
 * @author Administrator
 *
 */
public class MailDto {

	/**
	 * 发件人
	 */
	private String from;

	/**
	 * 收件人
	 */
	private String to;

	/**
	 * 抄送人
	 */
	private String cc;

	/**
	 * 密送人
	 */
	private String bcc;

	/**
	 * 邮件主题
	 */
	private String subject;

	/**
	 * 邮件内容
	 */
	private String content;

	/**
	 * 邮件发送时间
	 */
	private Date sentDate;

	/**
	 * 已读时间，当邮件未读时该属性为空
	 */
	private Date readDate;

	/**
	 * 邮件是否需要回执
	 */
	private boolean replySign;

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

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public Date getSentDate() {
		return sentDate;
	}

	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public boolean isReplySign() {
		return replySign;
	}

	public void setReplySign(boolean replySign) {
		this.replySign = replySign;
	}

}
