package com.bridgelabz.onlinebookstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.model.Book;
import com.bridgelabz.onlinebookstore.repository.BookRepository;
import com.bridgelabz.onlinebookstore.service.IBookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {
		
	@Autowired
	private IBookService bookService;
	
	@Autowired
	private BookRepository bookRepository;
	
	@PostMapping("/add")
	public ResponseEntity<ResponseDTO> addBook(@RequestHeader String token, @RequestBody BookDTO bookDTO) { 
		ResponseDTO responseDTO = bookService.addBook(token, bookDTO);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/allbooks")
	public ResponseEntity<ResponseDTO> getBooks(){
		List<Book> bookList = bookService.getAllBooks();
		ResponseDTO responseDTO = new ResponseDTO("List of Books", bookList);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ResponseDTO> search(@RequestParam String keyword){
//		List<Book> bookList = bookService.searchBook(keyword);
		List<Book> bookList = bookService.findAll(keyword);
		log.info("books by name ",bookList);
		ResponseDTO responseDTO = new ResponseDTO("Books By name", bookList);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@GetMapping("/bookauthor")
	public ResponseEntity<ResponseDTO> getBooksByAuthor(@RequestParam String bookAuthor){
		log.info("books by Author ");
		List<Book> bookList = bookService.getBookByAuthor(bookAuthor);
		ResponseDTO responseDTO = new ResponseDTO("Books By author", bookList);
	     return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	
	@PostMapping("/addallbooks")
	public ResponseEntity<ResponseDTO> addAllBooks(@RequestHeader String token){
		ResponseDTO responseDTO = bookService.addAllBook(token);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}	
}
