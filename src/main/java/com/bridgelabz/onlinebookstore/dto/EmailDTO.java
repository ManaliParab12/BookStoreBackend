package com.bridgelabz.onlinebookstore.dto;

import lombok.Data;

public @Data  class EmailDTO {
	
	private String email;
	private String subject;
	private String message;
	
	public EmailDTO() {
		
	}
	
	public EmailDTO(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

}
