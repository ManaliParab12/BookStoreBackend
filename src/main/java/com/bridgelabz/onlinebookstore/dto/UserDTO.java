package com.bridgelabz.onlinebookstore.dto;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

public @ToString @Data class UserDTO {

	@NotEmpty(message = "First Name cannot be Empty")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "First name is  Invalid")
	public String firstName;
	 
	@NotEmpty(message = "Last Name cannot be Empty")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Last name is Invalid")
	public String lastName;
	
	@NotEmpty(message = "Email cannot be Empty")
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Enter a valid email address")
	public String email;
	
	@Pattern(regexp= "(^$|[0-9]{10})", message = "Mobile number must be 10")
	public String phoneNumber;
	
	public String address;
	
	@NotEmpty(message = "Enter your password")
	@Length(min = 8, message = "Password must be at least 8 characters")
	@Pattern(regexp = "(?=.*[A-Z])(?=.*[0-9])(?=.*[!?#@$]{1})[a-zA-Z0-9!?#@$]{8,32}$", message = "Password invalid")
	public String password;
	
	public String type;
}
