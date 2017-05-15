package com.marsbase.springboot.service;

import java.util.Date;
import java.util.HashMap;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Value("${mail.enable}")
	private boolean enable;

	public void send(MimeMessagePreparator preparator) {
		if (enable) {
			mailSender.send(preparator);
		}
	}

	public void sendVerificationMail(String emailAddress) {

		HashMap<String, Object> model = new HashMap<>();

		model.put("test", "this is dynamic");

		String contenet = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				"/com/marsbase/springboot/velocity/verifyemail.vm", "UTF-8", model);

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {

				MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

				mimeMessageHelper.setTo(emailAddress);
				mimeMessageHelper.setFrom(new InternetAddress("no-reply@marsbase.com"));
				mimeMessageHelper.setSubject("Please verify your email address.");
				mimeMessageHelper.setSentDate(new Date());
				mimeMessageHelper.setText(contenet, true);
			}
		};

		send(preparator);
	}

}
