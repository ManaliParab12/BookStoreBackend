package com.bridgelabz.onlinebookstore.controller;

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
import com.bridgelabz.onlinebookstore.service.IOrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	IOrderService orderService;

	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addOrder(@RequestHeader("Email") String email, @RequestBody OrderDTO orderDTO) {
		ResponseDTO responseDTO = orderService.addOrder(email, orderDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<ResponseDTO> getUserOrders(@RequestHeader("Email") String email) {
		ResponseDTO responseDTO = orderService.getUserOrders(email);
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<ResponseDTO> getAllOrders() {
		ResponseDTO responseDTO = orderService.getAllOrders();
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}
}
