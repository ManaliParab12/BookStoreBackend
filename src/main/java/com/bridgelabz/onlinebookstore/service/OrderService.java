package com.bridgelabz.onlinebookstore.service;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Order;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.OrderRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.google.gson.Gson;


@Service
@PropertySource("classpath:status.properties")
public class OrderService implements IOrderService {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	OrderRepository orderRepository;
	
    @Autowired
    UserRepository userRepository;
    
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
	 @Autowired
	 private Binding bind;
	 
	 @Autowired
	 private Gson gson;
	 

	@Override
	public ResponseDTO addOrder(String email, OrderDTO orderDTO ) {
		orderDTO.setUser(userRepository.findByEmail(email)
									   .orElseThrow(() ->  new UserException(environment.getProperty("status.login.error.message"))));
		orderDTO.setDate(LocalDate.now());
		ModelMapper modelMapper = new ModelMapper();
		Order order = modelMapper.map(orderDTO, Order.class);
		User user = orderRepository.save(order).getUser();
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey(),
				gson.toJson(new EmailDTO(user.getEmail(), "Order Confirmed", "Hello " +user.getFirstName() + " "
																					  +user.getLastName() 
																					  + "\n Your Order has been placed successfully!"  
																					  + "\nOrder Details " 
																					  + "\nOrder date : " +order.getDate()
																					  + "\nOrder Id : #12345" 
																					  + "Thank You")));
		return new ResponseDTO("Your Order has been placed successfully!");
	}	 

		
	@Override
	public ResponseDTO  getUserOrders(String email) {
		List<Order> order = orderRepository.findAllOrdersByUser(userRepository.findByEmail(email)
	    							 .orElseThrow(() ->  new UserException(environment.getProperty("status.login.error.message"))));
		return new ResponseDTO("Your Orders" +order);
	}
	
	@Override
    public ResponseDTO  getAllOrders() {
		List<Order> order = orderRepository.findAll();
		return new ResponseDTO("Your Orders" +order);
    }
}
