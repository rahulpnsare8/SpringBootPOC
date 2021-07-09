package com.synechron.mymart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synechron.mymart.model.Order;
import com.synechron.mymart.service.OrderService;

@RestController
@RequestMapping("order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/getAll")
	public List<Order> getUsers() {
		 return  orderService.getAllOrders();
	}
	
	@PostMapping("/save")
	public void saveOrder(@RequestBody Order order) {
		orderService.placeOrder(order);
	}
	
	@GetMapping("/get")
	public Order getUser(@RequestParam int orderId) {
		return orderService.getOrder(orderId);
	}
	
	@GetMapping("/getByUserId")
	public List<Order> getOrderByUserId(@RequestParam int userId) {
		return orderService.getAllOrdersByUserId(userId);
	}

}
