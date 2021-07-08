package com.synechron.mymart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.synechron.mymart.model.Order;

@Service
public interface OrderService {
	
	public List<Order> getAllOrders();
	
	public Order getOrder(int orderId);
	
	public List<Order> getAllOrdersByUserId(int userId);
	
	public void placeOrder(Order order);

}
