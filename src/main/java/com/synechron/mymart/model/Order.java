package com.synechron.mymart.model;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	
	private int id;
	
	private Instant orderDate;
	
	private String orderStatus;
	
	private String paymentType;
	
	private int userId;
	
	private int productId;

}
