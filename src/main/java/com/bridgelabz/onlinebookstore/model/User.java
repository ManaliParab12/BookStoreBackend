
package com.bridgelabz.onlinebookstore.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import com.bridgelabz.onlinebookstore.dto.UserDTO;

import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "user")
public @Data @ToString class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
    private int id;
	
	@Column(name = "first_name")
    private String firstName;
	
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String password;
    private boolean isVerify;
    private String type;

    public User() {}
       
	public User(UserDTO userDTO) {
	    this.updateUser(userDTO);
	    }

	public void updateUser(UserDTO userDTO) {
		 this.firstName = userDTO.firstName;
		 this.lastName = userDTO.lastName;
		 this.email = userDTO.email;
		 this.phoneNumber = userDTO.phoneNumber;
		 this.address = userDTO.address;
		 this.password = userDTO.password;
		 this.type = userDTO.type;		
	}
}
