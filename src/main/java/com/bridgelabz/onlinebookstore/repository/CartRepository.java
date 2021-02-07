package com.bridgelabz.onlinebookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	Optional<Cart> findByBookAndUser(Book book, User user);
	
	List<Cart> findAllBooksByUser(User user);

	Cart deleteAllBooksByUser(User user);

	
//	@Query(value="delete from user, cart where user_id = id and user_id = :user_id", nativeQuery = true)
	Optional<Cart> deleteByUser(User user);

	void deleteCartByUser(User user);
}
