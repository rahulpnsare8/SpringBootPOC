package com.synechron.mymart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.synechron.mymart.dao.UserDao;
import com.synechron.mymart.entity.LoginEntity;
import com.synechron.mymart.entity.UserEntity;
import com.synechron.mymart.model.User;
import com.synechron.mymart.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<User> getUsers() {
		List<UserEntity> userEntityList = userDao.findAllUsers();
		List<User> userList = new ArrayList<>();
		for(UserEntity userEntity : userEntityList) {
			userList.add(userEntityToModel(userEntity));
		}
		return userList;
	}

	@Override
	public User saveUser(User user) {
		var userEntity = userModelToEntity(user);
		UserEntity savedEntity = userDao.save(userEntity);
		return userEntityToModel(savedEntity);
	}

	@Override
	public User getUser(int userId) {
		var userEntity = userDao.findById(userId);
		return userEntityToModel(userEntity);
	}
	
	public User userEntityToModel(UserEntity userEntity) {
		if(userEntity != null) {
			var user = new User();
			user.setUserId(userEntity.getId());
			user.setName(userEntity.getName());
			user.setAddress(userEntity.getAddress());
			user.setContactNumber(userEntity.getContactNumber());
			return user;
		}
		return null;
	}
	
	public UserEntity userModelToEntity(User user) {
		if(user != null) {
			var userEntity = new UserEntity();
			userEntity.setId(user.getUserId());
			userEntity.setName(user.getName());
			userEntity.setAddress(user.getAddress());
			userEntity.setContactNumber(user.getContactNumber());
			if(user.getCredentials() != null) {
				var loginEntity = new LoginEntity();
				loginEntity.setUserName(user.getCredentials().getUserName());
				loginEntity.setPassword(passwordEncoder.encode( user.getCredentials().getPassword()));
				loginEntity.setUserEntity(userEntity);
				userEntity.setLoginEntity(loginEntity);
			}
			return userEntity;
		}
		return null;
	}


}
