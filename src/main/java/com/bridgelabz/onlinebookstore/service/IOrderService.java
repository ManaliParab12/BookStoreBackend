package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Order;
public interface IOrderService {
	
	ResponseDTO addOrder(String email, OrderDTO orderDTO);

	List<Order> getUserOrders(String email);

	List<Order> getAllOrders();

}
