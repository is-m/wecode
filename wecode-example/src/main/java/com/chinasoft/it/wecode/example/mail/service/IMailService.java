package com.chinasoft.it.wecode.example.mail.service;

import java.io.UnsupportedEncodingException;
import java.util.function.Function;

import com.chinasoft.it.wecode.example.mail.dto.MailDto;
import com.chinasoft.it.wecode.example.mail.dto.MailQueryDto;
import com.chinasoft.it.wecode.example.mail.dto.MailResponseDto;

public interface IMailService {


	/**
	 * 邮件读取
	 * 
	 * @param mailQueryDto 邮件查询条件
	 * @param consumer     消费程序
	 */
	void read(MailQueryDto mailQueryDto, Function<MailDto, MailResponseDto> consumer);

	/**
	 * 邮件发送
	 * 
	 * @param mailDto
	 * @throws UnsupportedEncodingException
	 */
	void send(MailDto mailDto) throws UnsupportedEncodingException;
}
