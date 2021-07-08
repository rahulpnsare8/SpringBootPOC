package com.synechron.mymart.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "USER")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.AUTO)
	@Column(name = "USER_ID")
	private int id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "CONTACT_NUMBER")
	private long contactNumber;
	
	@OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private LoginEntity loginEntity;
	
}
