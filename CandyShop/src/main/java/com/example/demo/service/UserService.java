package com.example.demo.service;

import com.example.demo.dto.ChangePasswordRequestDTO;
import com.example.demo.dto.UserProfileRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;

public interface UserService {

	public User updateUser(String userId, UserProfileRequestDTO profileRequestDTO)
			throws Exception, ResourceNotFoundException;

	public void changePassword(String userId, ChangePasswordRequestDTO changePasswordRequestDTO)
			throws Exception, ResourceNotFoundException;

}