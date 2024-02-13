package com.codewithvarsha.blogapp2.services;

import java.util.List;

import com.codewithvarsha.blogapp2.payloads.UserDto;

public interface UserServices {
	
	UserDto createUSer(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
	UserDto registerNewUser(UserDto user);
}
