package com.synechron.mymart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synechron.mymart.entity.OrderEntity;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Integer>{

	public List<OrderEntity> findByUserId(int userId);
}
