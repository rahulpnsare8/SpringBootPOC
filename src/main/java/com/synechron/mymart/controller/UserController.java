package com.synechron.mymart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.mymart.model.User;
import com.synechron.mymart.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
			
	@GetMapping("/getAll")
	public List<User> getUsers() {
		return userService.getUsers();
	}
	
	@PostMapping("/register")
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@GetMapping("/get")
	public User getUser(@RequestParam int userId) {
		return userService.getUser(userId);
	}
	

}
