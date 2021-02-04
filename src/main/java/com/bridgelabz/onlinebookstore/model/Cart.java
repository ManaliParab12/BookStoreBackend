package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.CartDTO;

import lombok.Data;

@Entity
@Table(name = "cart", uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "user_id"}) )
public @Data class Cart {


	@Id
	@GeneratedValue
	private int id;
	
	private int quantity;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
    @JoinColumn(name = "book_id")
	private Book book;
	
	public Cart() {}
	
	
	public Cart(CartDTO cartDTO) {
		this.quantity = cartDTO.quantity;
		this.user = cartDTO.user;
		this.book = cartDTO.book;
	}
	
}