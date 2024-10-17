package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublisherRequestDTO {
	private String publisherName;
	private String phoneNumber;
	private String address;
	private String email;
}
