package com.bridgelabz.onlinebookstore.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}
