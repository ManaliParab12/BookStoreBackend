package com.bridgelabz.onlinebookstore.service;

import java.util.List;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.exception.BookException;
import com.bridgelabz.onlinebookstore.model.Book;

public interface IBookService {

	ResponseDTO addBook(String token, BookDTO bookDTO);

	ResponseDTO getAllBooks();

	ResponseDTO addAllBook(String token);

	ResponseDTO removeBook(int bookId, String token) throws BookException;

	List<Book> getBookByAuthor(String bookAuthor);

	List<Book> findAll(String keyword);

	ResponseDTO searchBooks(String search);

}