package com.bridgelabz.onlinebookstore.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bridgelabz.onlinebookstore.dto.OrderDTO;

import lombok.Data;

@Entity
@Table(name = "order_table")
public @Data class Order {

	@Id
	@GeneratedValue
	private int id;

	private int quantity;

	private int totalPrice;

	private LocalDate date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;

	public Order() {
	}

	public Order(OrderDTO orderDTO) {
		this.quantity = orderDTO.quantity;
		this.totalPrice = orderDTO.totalPrice;
		this.date = orderDTO.date;
		this.book = orderDTO.book;
		this.user = orderDTO.user;
	}

}
