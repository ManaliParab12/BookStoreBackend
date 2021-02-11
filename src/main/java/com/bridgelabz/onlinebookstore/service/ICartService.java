package com.bridgelabz.onlinebookstore.service;


import java.util.List;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.CartException;
import com.bridgelabz.onlinebookstore.model.Cart;


public interface ICartService {

	ResponseDTO addBookToCart(CartDTO cartDTO) throws CartException;

	ResponseDTO  updateCart(String token, int bookId, int quantity) throws CartException;

	List<Cart> getListOfBooksInCart(String token) throws CartException;

	ResponseDTO  removeBookFromCart(int bookId, String token) throws CartException;
	
//	ResponseDTO  removeAllBooksFromCart(String token);

//	ResponseDTO removeAllBooksFromCart(String email);
	
}
