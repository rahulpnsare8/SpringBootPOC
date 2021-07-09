package com.synechron.mymart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synechron.mymart.entity.UserEntity;

@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
	
	public UserEntity findById(int userId);
	
	public UserEntity findByName(String name);

    @Query("select userEntity from UserEntity userEntity left join fetch userEntity.loginEntity")
	public List<UserEntity> findAllUsers();
}
