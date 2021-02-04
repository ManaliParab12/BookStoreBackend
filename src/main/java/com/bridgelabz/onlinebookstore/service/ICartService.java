package com.bridgelabz.onlinebookstore.service;


import java.util.List;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Cart;


public interface ICartService {

	ResponseDTO addBookToCart(CartDTO cartDTO);

	ResponseDTO  updateCart(String token, int bookId, int quantity);

	List<Cart> getListOfBooksInCart(String token);

	ResponseDTO  removeBookFromCart(int bookId, String token);
	
	ResponseDTO  removeAllBooksFromCart(String email);
	
}
