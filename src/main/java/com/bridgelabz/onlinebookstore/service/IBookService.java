package com.bridgelabz.onlinebookstore.service;


import java.util.List;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Book;


public interface IBookService {

	ResponseDTO addBook(String token, BookDTO bookDTO);

	List<Book> getAllBooks();

	ResponseDTO addAllBook(String token);


}