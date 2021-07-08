package com.synechron.mymart.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ORDER_INFO")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "ORDER_DATE")
	private Instant orderDate;
	
	@Column(name = "ORDER_STATUS")
	private String orderStatus;
	
	@Column(name = "PAYMENT_TYPE")
	private String paymentType;
	
	@Column(name = "USER_ID")
	private int userId;
	
	@Column(name = "PRODUCT_ID")
	private int productId;
}
