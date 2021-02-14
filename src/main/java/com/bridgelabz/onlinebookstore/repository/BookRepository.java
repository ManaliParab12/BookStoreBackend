package com.bridgelabz.onlinebookstore.repository;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Book;

@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Integer> {
	
//	@Query(value="SELECT * FROM book b WHERE b.book_name OR b.book_author OR b.book_description LIKE %?1%", nativeQuery = true)
//	public  List<Book> findAll(String keyword);	
	
	@Query(value="SELECT * FROM book b WHERE b.book_author LIKE %?1%",  nativeQuery = true)
	List<Book> findByBookAuthor(String bookAuthor);
	
//	JPQL
	@Query("SELECT b FROM Book b WHERE b.bookName LIKE %?1%" + "OR b.bookAuthor LIKE %?1%" + "OR b.bookDescription LIKE %?1%")
	public  List<Book> findAll(String keyword);
	
}
