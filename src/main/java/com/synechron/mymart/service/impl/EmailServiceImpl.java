package com.synechron.mymart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.synechron.mymart.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
    private String username;  

	@Override
	public String sendEmail(int orderId) {
		var message = new SimpleMailMessage();
		message.setFrom(username);
		message.setTo("rahul.pansare@synechron.com");
		message.setText("Your order is successfull. Order number is "+orderId);
		message.setSubject ("Order Status");
		javaMailSender.send(message);
		return "success";
	}

}
