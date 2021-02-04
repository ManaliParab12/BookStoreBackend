package com.bridgelabz.onlinebookstore.repository;

import com.bridgelabz.onlinebookstore.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {

	Optional<User> findByEmail(String email);
   
}
	

