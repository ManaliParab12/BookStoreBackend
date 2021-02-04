package com.bridgelabz.onlinebookstore.dto;

import lombok.Data;

public @Data class ResponseDTO {
	private String message;
	private Object data;
	
	public ResponseDTO (String message) {
		this.message = message;
	}
	
	public ResponseDTO(String message, Object data) {
		this.message = message;
		this.data = data;
	}
	
	public static ResponseDTO getResponse(String message, Object data ) {
		return new ResponseDTO(message, data);
	}
}
