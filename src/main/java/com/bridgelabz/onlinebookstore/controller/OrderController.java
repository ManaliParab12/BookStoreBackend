package com.bridgelabz.onlinebookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.onlinebookstore.dto.OrderDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Order;
import com.bridgelabz.onlinebookstore.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	IOrderService orderService;
	
	@PostMapping("/add")
    public ResponseEntity<ResponseDTO> addOrder( @RequestHeader("Email") String email, @RequestBody OrderDTO orderDTO) {
		ResponseDTO responseDTO = orderService.addOrder( email, orderDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);	
    }
	
	
	@GetMapping("/get")
	public ResponseEntity<List<Order>> getUserOrders(@RequestHeader("email") String email) {
	    return new ResponseEntity<>(orderService.getUserOrders(email), HttpStatus.OK);
	}

}
