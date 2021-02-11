package com.bridgelabz.onlinebookstore.repository;


import java.util.List;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
	
//	@Query(value="select * from bookstore, book where "   )
	List<Book> findByBookNameAndBookAuthorAndBookDescriptionAndBookPrice(String bookName, String bookAuthor , String bookDescription, double bookPrice);

}
