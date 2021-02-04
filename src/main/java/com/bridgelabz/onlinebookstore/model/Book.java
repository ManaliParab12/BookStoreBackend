package com.bridgelabz.onlinebookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.bridgelabz.onlinebookstore.dto.BookDTO;
import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Entity
@Table(name = "book")
public @Data class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "book_id")
	private int id;
	
	@CsvBindByName(column = "title")
	private String bookName;
	
	@CsvBindByName(column = "author")
	private String bookAuthor;
	
	@CsvBindByName(column = "image")
	private String bookImage;
	
	private int bookQuantity;
	
	@CsvBindByName(column = "price")
	private double bookPrice;
	
	@CsvBindByName(column = "description")
	@Lob
	private String bookDescription;
	

	public Book() { }
	
	public Book(BookDTO bookDTO) {
		this.bookName = bookDTO.bookName;
		this.bookAuthor = bookDTO.bookAuthor;
		this.bookImage = bookDTO.bookImage;
		this.bookQuantity = bookDTO.bookQuantity;
		this.bookPrice = bookDTO.bookPrice;
		this.bookDescription = bookDTO.bookDescription;
	}	
	
	public Book(int id, String bookName, String bookAuthor, String bookImage, int bookQuantity, double bookPrice, String bookDescription) {
		this.id = id;
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
		this.bookImage = bookImage;
		this.bookQuantity = bookQuantity;
		this.bookPrice = bookPrice;
		this.bookDescription = bookDescription;
	}
}
