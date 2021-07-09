package com.synechron.mymart.authentication;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.synechron.mymart.dao.LoginDao;

@Component
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private LoginDao loginDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var optionalLoginEntity = loginDao.findByUserName(username);
		if(optionalLoginEntity.isPresent()) {
			var loginEntity = optionalLoginEntity.get();
			return new User(loginEntity.getUserName(),loginEntity.getPassword(), new ArrayList<>());
		}
		return null;
	}
		
}
