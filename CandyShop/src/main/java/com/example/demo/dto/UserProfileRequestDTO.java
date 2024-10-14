package com.example.demo.dto;

import java.time.LocalDate;

import com.example.demo.model.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileRequestDTO {
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private Gender gender;
	private LocalDate birthDay;
}
