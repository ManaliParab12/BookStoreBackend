package com.bridgelabz.onlinebookstore.dto;

import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.model.User;

import lombok.Data;

public @Data class CartDTO {
	public int quantity;
	public User user;
	public Book book;
}
