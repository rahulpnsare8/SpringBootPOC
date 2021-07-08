package com.synechron.mymart.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.mymart.entity.LoginEntity;

@Repository
public interface LoginDao extends JpaRepository<LoginEntity, Integer>{

	public Optional<LoginEntity> findByUserName(String userName);
}
