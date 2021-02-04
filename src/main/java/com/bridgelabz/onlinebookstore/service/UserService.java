package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.onlinebookstore.dto.EmailDTO;
import com.bridgelabz.onlinebookstore.dto.ResponseDTO;
import com.bridgelabz.onlinebookstore.dto.UserDTO;
import com.bridgelabz.onlinebookstore.exception.UserException;
import com.bridgelabz.onlinebookstore.model.User;
import com.bridgelabz.onlinebookstore.repository.UserRepository;
import com.bridgelabz.onlinebookstore.utility.Token;
import com.google.gson.Gson;

@Service
public class UserService implements IUserService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RabbitTemplate rabbitTemplate;
	 
	 @Autowired
	 private Binding bind;
		
	 @Autowired
	 private Gson gson;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 public ResponseDTO registerUser(UserDTO userDTO) throws UserException {
		 String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		 User user = new User(userDTO);
	     if(userRepository.findByEmail(user.getEmail()).isPresent())
	    	 throw new UserException("User is Already Registered with this Email Id");
	     user.setPassword(password);
	     userRepository.save(user);
	     verificationMail(user);	     
	     return new ResponseDTO("User Registered successfully");
	}
	 
	public Optional<User> getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	
	@Override
	public  List<User> getAllUser() {
		return userRepository.findAll();
	}

	
	@Override
	public ResponseDTO updateUser(String email, UserDTO userDTO) {
		 User user = userRepository.findByEmail(email).get();
		 user.updateUser(userDTO);
	     userRepository.save(user);
	     return ResponseDTO.getResponse("User Details updated", user);	     
	}
	
	@Override
    public ResponseDTO deleteUser(String email) {
        User user = this.getUserByEmail(email).get();
        userRepository.delete(user);
        return ResponseDTO.getResponse("User Deleted", user);
    }
	
	@Override
	public ResponseDTO verificationMail(User user) {
		String token = Token.generateToken(user.getId());
		//	emailService.sendMail(user.getEmail(), "verification ", getVerificationURL(token));
			rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey()
				,gson.toJson(new EmailDTO(user.getEmail(), "verification Link ", getVerificationURL(token))));
			return new ResponseDTO("verification mail sent");
	}
	
	@Override
	public ResponseDTO verifyUser(String token) {
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId).get();
		user.setVerify(true);
		userRepository.save(user);
		return ResponseDTO.getResponse("Verified Successfully", user);
	}

	@Override
	public ResponseDTO userLogin(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail())
				.orElseThrow(() -> new UserException("User Not Found"));
		//String token = Token.generateToken(user.getId());
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()) && user.isVerify())
			return new ResponseDTO("login Successfull");
		throw new UserException("User not Verified");
	}

	private String getVerificationURL(String token) {
		System.out.println("token " +token);
		return "Click on below link \n" +
				"http://localhost:8080/swagger-ui.html#!/user-controller/verifyUserUsingGET" +
				"\n token : " +token;
	}

	
	@Override
	public ResponseDTO forgetPassword(String email) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User Not Found"));
		String token = Token.generateToken(user.getId());
		//emailService.sendMail(user.getEmail(), "verification ",resetURL(token));
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey()
				,gson.toJson(new EmailDTO(user.getEmail(), "Reset Password Link ",  resetURL(token))));
		return new ResponseDTO("Reset Password link sent to your registered email id" );
	}

	
	private String resetURL(String token) {
		return "Click on below link to Reset your Password \n" +
				"http://localhost:8080/swagger-ui.html#!/user-controller/resetPasswordUsingPOST" +
				"\n token : " +token;				
	}

	
	@Override
	public ResponseDTO updatePassword(String token, UserDTO userDTO) {
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId).get();
		user.setPassword(userDTO.password);
		userRepository.save(user);
		return ResponseDTO.getResponse("Password Changed successfully", user);
	}	
}