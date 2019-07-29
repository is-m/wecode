package com.chinasoft.it.wecode.example.mail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

	@Value("${mail.debug:false}")
	private boolean debug;

	@Value("${mail.account:liaoxianmu}")
	private String account;

	@Value("${mail.password:888888}")
	private String password;

	// pop3 用于处理接收邮件
	@Value("${mail.pop3.account}")
	private String pop3User;

	@Value("${mail.pop3.password}")
	private String pop3Password;

	@Value("${mail.pop3.host:localhost}")
	private String pop3Host;

	@Value("${mail.pop3.port:110}")
	private String pop3Port;

	// QQl邮箱时，使用SSLSkcketFactory配置
	// javax.net.ssl.SSLSocketFactory
	@Value("${mail.pop3.socketFactory.class:}")
	private String pop3SocketFactoryClass;

	// true
	@Value("${mail.pop3.socketFactory.fallback:true}")
	private boolean pop3SocketFactoryFallback;

	// 995
	@Value("${mail.pop3.socketFactory.port:}")
	private String pop3SocketFactoryPort;

	// smtp 用于发送邮件
	@Value("${mail.smtp.account}")
	private String smtpUser;

	@Value("${mail.smtp.password}")
	private String smtpPassword;

	@Value("${mail.smtp.host:localhost}")
	private String smtpHost;

	@Value("${mail.smtp.port:25}")
	private String smtpPort;

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPop3Host() {
		return pop3Host;
	}

	public void setPop3Host(String pop3Host) {
		this.pop3Host = pop3Host;
	}

	public String getPop3Port() {
		return pop3Port;
	}

	public void setPop3Port(String pop3Port) {
		this.pop3Port = pop3Port;
	}

	public String getPop3SocketFactoryClass() {
		return pop3SocketFactoryClass;
	}

	public void setPop3SocketFactoryClass(String pop3SocketFactoryClass) {
		this.pop3SocketFactoryClass = pop3SocketFactoryClass;
	}

	public boolean isPop3SocketFactoryFallback() {
		return pop3SocketFactoryFallback;
	}

	public void setPop3SocketFactoryFallback(boolean pop3SocketFactoryFallback) {
		this.pop3SocketFactoryFallback = pop3SocketFactoryFallback;
	}

	public String getPop3SocketFactoryPort() {
		return pop3SocketFactoryPort;
	}

	public void setPop3SocketFactoryPort(String pop3SocketFactoryPort) {
		this.pop3SocketFactoryPort = pop3SocketFactoryPort;
	}

	public String getSmtpHost() {
		return smtpHost;
	}

	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}

	public String getSmtpPort() {
		return smtpPort;
	}

	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}

	public String getPop3User() {
		return pop3User;
	}

	public void setPop3User(String pop3User) {
		this.pop3User = pop3User;
	}

	public String getPop3Password() {
		return pop3Password;
	}

	public void setPop3Password(String pop3Password) {
		this.pop3Password = pop3Password;
	}

	public String getSmtpUser() {
		return smtpUser;
	}

	public void setSmtpUser(String smtpUser) {
		this.smtpUser = smtpUser;
	}

	public String getSmtpPassword() {
		return smtpPassword;
	}

	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}

}
