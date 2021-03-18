package com.bridgelabz.onlinebookstore.dto;

import java.time.LocalDate;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.User;

import lombok.Data;

public @Data class OrderDTO {
	public int quantity;
	public int totalPrice;
	public LocalDate date;
	public User user;
	public Book book;

}
