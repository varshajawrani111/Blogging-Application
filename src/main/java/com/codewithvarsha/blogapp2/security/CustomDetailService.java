package com.codewithvarsha.blogapp2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithvarsha.blogapp2.entities.User;
import com.codewithvarsha.blogapp2.exceptions.ResourceNotFoundException;
import com.codewithvarsha.blogapp2.repositories.UserRepo;

@Service
public class CustomDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// loading user from database by user name
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> 
		new ResourceNotFoundException("User", "with Email :"+ username, 0));
		
		return user;
	}
	
	

}
