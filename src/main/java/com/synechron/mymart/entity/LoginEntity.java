package com.synechron.mymart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "LOGIN")
public class LoginEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private int id;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="USER_ID", referencedColumnName = "user_id", nullable = false )
	private UserEntity userEntity;
	
}
