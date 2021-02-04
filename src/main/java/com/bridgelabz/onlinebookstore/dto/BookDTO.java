package com.bridgelabz.onlinebookstore.dto;

import lombok.Data;

public @Data class BookDTO {
	
	public String bookName;
	public String bookAuthor;
	public String bookImage;
	public int bookQuantity;
	public double bookPrice;
	public String bookDescription;
}
