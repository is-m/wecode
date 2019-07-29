package com.chinasoft.it.wecode.example.mail.inbox;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinasoft.it.wecode.example.mail.dto.MailDto;
import com.chinasoft.it.wecode.example.mail.service.IMailService;

@Component
public class SimpleMailReadScheduler {

	@Autowired
	private IMailService mailService;

	@Scheduled(cron = "0/20 * * * * ? ")
	public void readMail() throws Exception {

		//List<MailDto> read = mailService.read(null);

	}
	
	// @Scheduled(cron = "0/29 * * * * ? ")
	public void writeMail() throws Exception {

		MailDto mailDto = new MailDto();
		mailDto.setFrom("liaofeng");
		mailDto.setTo("liaoxianmu");
		mailDto.setSubject("send a test mail at " + System.currentTimeMillis());
		mailDto.setContent(System.currentTimeMillis()+"");
		mailService.send(mailDto);

	}
	

}
