package com.codewithvarsha.blogapp2.security;

import com.codewithvarsha.blogapp2.payloads.UserDto;

import lombok.Data;

@Data
public class JwtAuthResponse {
	
	private String token;
	
	private UserDto user;

}
