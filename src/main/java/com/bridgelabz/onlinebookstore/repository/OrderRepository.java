package com.bridgelabz.onlinebookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Cart;
import com.bridgelabz.onlinebookstore.model.Order;
import com.bridgelabz.onlinebookstore.model.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

	List<Order> findAllOrdersByUser(User user);

}
