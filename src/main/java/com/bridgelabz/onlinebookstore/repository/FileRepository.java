package com.bridgelabz.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.onlinebookstore.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Integer>  {

}
