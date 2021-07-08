package com.synechron.mymart.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
	
	public String sendEmail(int orderId);

}
