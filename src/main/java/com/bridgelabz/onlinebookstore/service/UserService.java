package com.bridgelabz.onlinebookstore.service;

import java.util.List;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource("classpath:status.properties")
public class UserService implements IUserService {
	
	@Autowired
	private Environment environment;
	
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
	     if(user.isVerify()) {
	    	 verificationMail(user);
	    	 return new ResponseDTO("Registration successful, And verification link has been sent to your email id: " +user.getEmail());
	     } else {
	    	 return new ResponseDTO("Registration has been Faild");
	     }
	}
	 
		@Override
		public ResponseDTO verificationMail(User user) {
			String token = Token.generateToken(user.getId());
				rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey()
					,gson.toJson(new EmailDTO(user.getEmail(), "verification Link ", getVerificationURL(token))));
				return new ResponseDTO("verification link has been sent to your registered email id : " +user.getEmail());
		}
		
		@Override
		public ResponseDTO verifyUser(String token) {
			int userId = Token.decodeToken(token);
			User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
			user.setVerify(true);
			userRepository.save(user);
			return ResponseDTO.getResponse("Verified Successfully", user);
		}

	@Override
	public ResponseDTO userLogin(UserDTO userDTO) {
		User user = userRepository.findByEmail(userDTO.getEmail())
					.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		if(bCryptPasswordEncoder.matches(userDTO.getPassword(), user.getPassword()) && user.isVerify())
			return new ResponseDTO(environment.getProperty("status.login.message"));
		throw new UserException(("Identity Verification, Action Required!"));
	}
	 
	public ResponseDTO getUserByEmail(String email) {
		userRepository.findByEmail(email)
					  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		 return new ResponseDTO("User Details");
	}
	
	
	@Override
	public  List<User> getAllUser() {
		return userRepository.findAll();
	}

	
	@Override
	public ResponseDTO updateUser(String email, UserDTO userDTO) {
		 User user = userRepository.findByEmail(email)
				 				   .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		 user.updateUser(userDTO);
	     userRepository.save(user);
	     return ResponseDTO.getResponse("Record updated successfully", user);	     
	}
	
	@Override
    public ResponseDTO deleteUser(String email) {
        User user =  userRepository.findByEmail(email)      		
        						   .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
        userRepository.delete(user);
        return ResponseDTO.getResponse("Record has been successfully deleted", user);
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
				.orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		String token = Token.generateToken(user.getId());
		rabbitTemplate.convertAndSend(bind.getExchange(), bind.getRoutingKey()
				,gson.toJson(new EmailDTO(user.getEmail(), "Reset Password Link ",  resetURL(token))));
		return new ResponseDTO("Reset Password link has been sent to your registered email id : " +user.getEmail());
	}

	
	private String resetURL(String token) {
		return "Click on below link to Reset your Password \n" +
				"http://localhost:8080/swagger-ui.html#!/user-controller/resetPasswordUsingPOST" +
				"\n token : " +token;				
	}

	
	@Override
	public ResponseDTO updatePassword(String token, UserDTO userDTO) {
		String password = bCryptPasswordEncoder.encode(userDTO.getPassword());
		int userId = Token.decodeToken(token);
		User user = userRepository.findById(userId)
								  .orElseThrow(() -> new UserException(environment.getProperty("status.login.error.message")));
		user.setPassword(password);
		userRepository.save(user);
		return ResponseDTO.getResponse("Your password has been changed successfully!", user);
	}	
}