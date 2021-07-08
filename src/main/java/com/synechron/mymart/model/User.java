package com.synechron.mymart.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	
	private int userId;
	
	private String name;
	
	private String address;
	
	private long contactNumber;
	
	private Login credentials;

}
