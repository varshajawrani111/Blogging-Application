package com.codewithvarsha.blogapp2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvarsha.blogapp2.payloads.UserDto;
import com.codewithvarsha.blogapp2.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users/")
public class UserController {
	
	@Autowired
	private UserServices userService;
	
	//POST - creating the user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		
		UserDto createUSerDto = this.userService.createUSer(userDto);
		
		return new ResponseEntity<>(createUSerDto, HttpStatus.CREATED);
	}
	
	//PUT - update the user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		
		UserDto updatedUser = this.userService.updateUser(userDto,uid);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	//GET -get all users
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> userDtos = this.userService.getAllUsers();
		 
		return ResponseEntity.ok(userDtos);
	}
	
	//GET -get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId")Integer userId){
		
		UserDto userDto = this.userService.getUserById(userId);
		
		return ResponseEntity.ok(userDto);
	}
	
	//DELETE - deleting the user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer uid){
		
		this.userService.deleteUser(uid);
		return ResponseEntity.ok("User deleted successfully!.");
	}
	
	
	
}
