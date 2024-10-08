package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
	private String message;
	private int status;
	private long timestamp;

	public ResponseDTO(String message, int status) {
		this.message = message;
		this.status = status;
		this.timestamp = System.currentTimeMillis();
	}
}
