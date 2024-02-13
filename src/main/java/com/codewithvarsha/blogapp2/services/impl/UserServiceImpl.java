package com.codewithvarsha.blogapp2.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithvarsha.blogapp2.config.AppConstants;
import com.codewithvarsha.blogapp2.entities.Role;
import com.codewithvarsha.blogapp2.entities.User;
import com.codewithvarsha.blogapp2.exceptions.ResourceNotFoundException;
import com.codewithvarsha.blogapp2.payloads.UserDto;
import com.codewithvarsha.blogapp2.repositories.RoleRepo;
import com.codewithvarsha.blogapp2.repositories.UserRepo;
import com.codewithvarsha.blogapp2.services.UserServices;

@Service
public class UserServiceImpl implements UserServices {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUSer(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUSer = this.userRepo.save(user);
		return this.userToDto(savedUSer);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","id", userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();		
		
		List<UserDto> userDtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","id", userId));
		
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(UserDto userDto) {
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		
		//Instead of doing the manually a sabove we now make use of modelmapper to this mapping automaically
		User user = this.modelMapper.map(userDto, User.class);
		
		return user;
	}
	
	private UserDto userToDto(User user) {
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
	}
	
	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}
	
	

}
