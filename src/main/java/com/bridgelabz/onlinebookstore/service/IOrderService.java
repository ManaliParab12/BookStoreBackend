package com.bridgelabz.onlinebookstore.service;

import com.bridgelabz.onlinebookstore.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;

public interface IOrderService {
	
	ResponseDTO addOrder(String email, OrderDTO orderDTO);

	ResponseDTO getUserOrders(String email);

	ResponseDTO getAllOrders();
}
