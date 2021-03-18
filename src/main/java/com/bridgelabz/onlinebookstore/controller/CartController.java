package com.bridgelabz.onlinebookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.CartException;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.service.ICartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addBookToCart(@RequestBody CartDTO cartDTO) throws CartException {
		ResponseDTO responseDTO = cartService.addBookToCart(cartDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@PutMapping("/{quantity}")
	public ResponseEntity<ResponseDTO> updateCart(@RequestHeader("Token") String token,
			@RequestParam(value = "book_id") int bookId, @RequestParam int quantity) throws CartException {
		ResponseDTO responseDTO = cartService.updateCart(token, bookId, quantity);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	@GetMapping("/get")
	public ResponseEntity<List<Cart>> getListOfBooksInCart(@RequestHeader("email") String email) throws CartException {
		return new ResponseEntity<>(cartService.getListOfBooksInCart(email), HttpStatus.OK);
	}

	@DeleteMapping("/remove-book/{book-id}")
	public ResponseEntity<ResponseDTO> removeBookFromCart(@PathVariable(value = "book-id") int bookId,
			@RequestHeader("Token") String token) throws CartException {
		ResponseDTO responseDTO = cartService.removeBookFromCart(bookId, token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
}
