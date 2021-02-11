package com.bridgelabz.onlinebookstore.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgelabz.onlinebookstore.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Order;
import com.bridgelabz.onlinebookstore.repository.OrderRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;


@Service
public class OrderService implements IOrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
    @Autowired
    UserRepository userRepository;

	@Override
	public ResponseDTO addOrder(String email, OrderDTO orderDTO ) {
		orderDTO.setUser(userRepository.findByEmail(email).get());
		orderDTO.setDate(LocalDate.now());
		Order order  = new Order(orderDTO);
		orderRepository.save(order);
		return new ResponseDTO("Order place");
	}
	
	@Override
	public List<Order> getUserOrders(String email) {
	    return orderRepository.findAllOrdersByUser(userRepository.findByEmail(email).get());
	}
	
	@Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
