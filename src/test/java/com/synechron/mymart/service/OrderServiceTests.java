package com.synechron.mymart.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.synechron.mymart.dao.OrderDao;
import com.synechron.mymart.entity.OrderEntity;
import com.synechron.mymart.model.Order;
import com.synechron.mymart.service.impl.OrderServiceImpl;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {
	
	@Mock
	OrderDao orderDao;
	
	@InjectMocks
	OrderServiceImpl orderService;
	
	
	@Test
	void testGetAllOrdersSuccess(@Mock OrderEntity orderEntity) {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		orderEntityList.add(orderEntity);
		Mockito.when(orderDao.findAll()).thenReturn(orderEntityList);
		Assertions.assertEquals(1, orderService.getAllOrders().size());
	}
	
	
	@Test
	void testGetAllOrdersFailure() {
		try {
		Mockito.when(orderDao.findAll()).thenThrow(new SQLException("No value present"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		List<Order> list = orderService.getAllOrders();
		Assertions.assertNotNull(list);
		Assertions.assertEquals(0, list.size());
	}
	
	@Test
	void testGetOrderSuccess() {
		int orderId= 1; 	
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(orderId);
		Mockito.when(orderDao.findById(orderId)).thenReturn(Optional.of(orderEntity));
		Assertions.assertEquals(orderId, orderService.getOrder(orderId).getId());
	}
	
	
	@Test
	void testGetAllOrdersByUserIdSuccess(@Mock OrderEntity orderEntity) {
		List<OrderEntity> orderEntityList = new ArrayList<OrderEntity>();
		orderEntityList.add(orderEntity);
		int userId=65;
		Mockito.when(orderDao.findByUserId(userId)).thenReturn(orderEntityList);
		Assertions.assertEquals(1, orderService.getAllOrdersByUserId(userId).size());
	}

}
