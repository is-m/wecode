package com.chinasoft.it.wecode.example.mail.dto;

import java.util.Date;

public class MailQueryDto {

	/**
	 * 邮件标题
	 */
	private String subject;

	/**
	 * 邮件内容
	 */
	private String content;

	/**
	 * 读取状态（默认：全部）
	 */
	private ReadState readState = ReadState.ALL;

	/**
	 * 发件人
	 */
	private String from;

	/**
	 * 收件人
	 */
	private String to;

	/**
	 * 邮件发送开始时间
	 */
	private Date sentBeginTime;

	/**
	 * 邮件发送结束时间
	 */
	private Date sentEndTime;

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

	public ReadState getReadState() {
		return readState;
	}

	public void setReadState(ReadState readState) {
		this.readState = readState;
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

	public Date getSentBeginTime() {
		return sentBeginTime;
	}

	public void setSentBeginTime(Date sentBeginTime) {
		this.sentBeginTime = sentBeginTime;
	}

	public Date getSentEndTime() {
		return sentEndTime;
	}

	public void setSentEndTime(Date sentEndTime) {
		this.sentEndTime = sentEndTime;
	}

}
