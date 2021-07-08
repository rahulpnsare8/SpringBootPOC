package com.synechron.mymart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synechron.mymart.dao.OrderDao;
import com.synechron.mymart.entity.OrderEntity;
import com.synechron.mymart.model.Order;
import com.synechron.mymart.service.EmailService;
import com.synechron.mymart.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public List<Order> getAllOrders() {
		List<OrderEntity> orderEntityList = orderDao.findAll();
		List<Order> orderList = new ArrayList<>();
		for(OrderEntity entity : orderEntityList) {
			orderList.add(orderEntityToModel(entity));
		}
		return orderList;
	}

	@Override
	public Order getOrder(int orderId) {
		Optional<OrderEntity> optional = orderDao.findById(orderId);
		OrderEntity orderEntity = optional.get();
		return orderEntityToModel(orderEntity);
	}

	@Override
	public List<Order> getAllOrdersByUserId(int userId) {
		List<OrderEntity> orderEntityList = orderDao.findByUserId(userId);
		List<Order> orderList = new ArrayList<>();
		for(OrderEntity entity : orderEntityList) {
			orderList.add(orderEntityToModel(entity));
		}
		return orderList;
	}

	@Override
	public void placeOrder(Order order) {
		OrderEntity save = orderDao.save(orderModelToEntity(order));
		if(save.getId() >0) {
			emailService.sendEmail(save.getId());
		}
	}
	
	private Order orderEntityToModel(OrderEntity orderEntity) {
		Order order = new Order();
		order.setId(orderEntity.getId());
		order.setOrderDate(orderEntity.getOrderDate());
		order.setOrderStatus(orderEntity.getOrderStatus());
		order.setPaymentType(orderEntity.getPaymentType());
		order.setProductId(orderEntity.getProductId());
		order.setUserId(orderEntity.getUserId());
		
		return order;
	}
	
	private OrderEntity orderModelToEntity(Order order) {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(order.getId());
		orderEntity.setOrderDate(order.getOrderDate());
		orderEntity.setOrderStatus(order.getOrderStatus());
		orderEntity.setPaymentType(order.getPaymentType());
		orderEntity.setProductId(order.getProductId());
		orderEntity.setUserId(order.getUserId());
		
		return orderEntity;
	}

}
