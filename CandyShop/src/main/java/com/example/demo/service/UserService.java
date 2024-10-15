package com.example.demo.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.ChangeEmailRequestDTO;
import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;

public interface UserService {

	public User updateUser(String userId, UserProfileRequestDTO profileRequestDTO)
			throws Exception, ResourceNotFoundException;

	public void changePassword(String userId, ChangePasswordRequestDTO changePasswordRequestDTO)
			throws Exception, ResourceNotFoundException;
	
	public User uploadAvatar(String userId, MultipartFile multipartFile) throws IOException, Exception;
	
	public User changeEmail(String userId, ChangeEmailRequestDTO changeEmailRequestDTO) throws Exception, ResourceNotFoundException;

}
