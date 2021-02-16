package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.model.Book;

public interface IElasticService {
	
	Book add(Book book);

	List<Book> searchBooksByAuthor(String search);

	Book updateBookById(String id, Book book);
	
	public void deleteBookById(String id);

}
