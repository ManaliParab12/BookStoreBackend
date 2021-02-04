package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;

public interface IUserService  {
	
	ResponseDTO  registerUser(UserDTO userDTO) throws UserException;

	Optional<User> getUserByEmail(String email);

	List<User> getAllUser();

	ResponseDTO  updateUser(String email, UserDTO userDTO);

	ResponseDTO deleteUser(String email);

	ResponseDTO userLogin(UserDTO userDTO);	
	
	ResponseDTO verificationMail(User user);
	
	ResponseDTO verifyUser(String token);

	ResponseDTO forgetPassword(String email);

	ResponseDTO updatePassword(String token, UserDTO userDTO);

}
