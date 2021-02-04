package com.bridgelabz.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByBookAndUser(Book book, User user);
	
	List<Cart> findAllBooksByUser(User user);

	Cart deleteAllBooksByUser(User user);

	void deleteCartByUserEquals(User user);

}
