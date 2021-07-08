package com.synechron.mymart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synechron.mymart.model.User;

@Service
public interface UserService {

	public List<User> getUsers();
	
	public User saveUser(User user);
	
	public User getUser(int userId);
	
}
