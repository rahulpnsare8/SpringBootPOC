package com.synechron.mymart.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.synechron.mymart.dao.UserDao;
import com.synechron.mymart.entity.UserEntity;
import com.synechron.mymart.model.User;
import com.synechron.mymart.service.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {
	
	@Mock UserDao userDao;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@Test
	void testGetUsersSuccess(@Mock UserEntity userEntity) {
		List<UserEntity> userEntityList = new ArrayList<UserEntity>();
		userEntityList.add(userEntity);
		Mockito.when(userDao.findAllUsers()).thenReturn(userEntityList);
		Assertions.assertEquals(1, userService.getUsers().size());
	}
	
	@Test
	void testGetUsersFailure(@Mock UserEntity userEntity) {
		try {
		Mockito.when(userDao.findAllUsers()).thenThrow(new SQLException("No value present"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<User> list = userService.getUsers();
		Assertions.assertNotNull(list);
		Assertions.assertEquals(0, list.size());
	}
	
	@Test
	void testSaveUserSuccess() {
		User user =new User();
		user.setName("Abc");
		UserEntity userEntity = new UserEntity();
		userEntity.setName("Abc");
		UserEntity userEntity1 =  Mockito.spy(userEntity);
		UserServiceImpl userServiceImpl = Mockito.spy(userService);
		Mockito.doReturn(userEntity).when(userServiceImpl).userModelToEntity(user);
		Mockito.doReturn(userEntity1).when(userDao).save(userEntity);
		Assertions.assertEquals("Abc", userServiceImpl.saveUser(user).getName());
	}
	
	@Test
	void testGetUserSuccess() {
		int userId= 65; 	
		UserEntity userEntity = new UserEntity();
		userEntity.setId(userId);
		Mockito.when(userDao.findById(userId)).thenReturn(userEntity);
		Assertions.assertEquals(userId, userService.getUser(userId).getUserId());
	}
	
	
}
