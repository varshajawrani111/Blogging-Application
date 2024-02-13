package com.codewithvarsha.blogapp2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	
	private String message;
	private boolean success;
	//we can add more what ever is required to be shown this is known as custom exception handling
}
