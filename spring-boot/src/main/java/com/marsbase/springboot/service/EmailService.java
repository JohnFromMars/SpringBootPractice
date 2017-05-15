package com.marsbase.springboot.service;

import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.enable}")
	private boolean enable;

	public void send(MimeMessagePreparator preparator) {
		if (enable) {
			mailSender.send(preparator);
		}
	}

	public void sendVerificationMail(String emailAddress) {
		StringBuilder stringBuilder = new StringBuilder();
		
		stringBuilder.append("<HTML>");
		stringBuilder.append("<p>");
		stringBuilder.append("Hello there, this is <strong>HTML</strong>");
		stringBuilder.append("</p>");
		stringBuilder.append("</HTML>");
		
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			
			

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

				mimeMessageHelper.setTo(emailAddress);
				mimeMessageHelper.setFrom(new InternetAddress("no-reply@marsbase.com"));
				mimeMessageHelper.setSubject("Please verify your email address.");
				mimeMessageHelper.setSentDate(new Date());

				mimeMessageHelper.setText(stringBuilder.toString(),true);
			}
		};
		
		send(preparator);
	}

}
