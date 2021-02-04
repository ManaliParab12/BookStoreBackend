package com.bridgelabz.onlinebookstore.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.CartDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.repository.BookRepository;
import com.bridgelabz.onlinebookstore.repository.CartRepository;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;



@Service
public class CartService implements ICartService {
	
	@Autowired
	private CartRepository cartRepository;	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	
	@Override
	public ResponseDTO addBookToCart(CartDTO cartDTO) {
		Cart cart = new Cart(cartDTO);
		cartRepository.save(cart);
		return new ResponseDTO("Book Added to cart");
	}


	@Override
	public ResponseDTO updateCart(String token, int bookId, int quantity) {
		int userId = Token.decodeToken(token);
		Optional<Cart> carts = cartRepository.findByBookAndUser(bookRepository.findById(bookId).get()
	                , userRepository.findById(userId).get());
	    Cart cart = carts.orElseThrow(() -> new UserException("INVALID_USER"));
	    cart.setQuantity(quantity);
	    cartRepository.save(cart);
	    return new  ResponseDTO("Book Quantity updated"); 
	}
	
	@Override
	public List<Cart> getListOfBooksInCart(String email) {
	    return cartRepository.findAllBooksByUser(userRepository.findByEmail(email).get());
	}
	 
	@Override
	public ResponseDTO removeBookFromCart(int bookId, String token) {
		int userId = Token.decodeToken(token);
		Optional<Cart> carts = cartRepository.findByBookAndUser(bookRepository.findById(bookId).get()
		                , userRepository.findById(userId).get());
		Cart cart = carts.orElseThrow(() -> new RuntimeException("INVALID_BOOK_ID"));
		cartRepository.deleteById(cart.getId());
		return new ResponseDTO("Book Removed from cart"); 	
	}


//	@Override
//	public Cart removeAllBooks(String token) {
//		int userId = Token.decodeToken(token);
//		return cartRepository.deleteAllBooksByUser(userRepository.findById(userId).get());		
//	}
	
	
	@Override
	   public ResponseDTO  removeAllBooksFromCart(String email) {
	       cartRepository.deleteCartByUserEquals(userRepository.findByEmail(email).get());
	       return new ResponseDTO("Cart is empty"); 
	    }
}