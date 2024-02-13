package com.codewithvarsha.blogapp2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvarsha.blogapp2.entities.Category;
import com.codewithvarsha.blogapp2.entities.Post;
import com.codewithvarsha.blogapp2.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	//for searching the posts
	List<Post> findByTitleContaining(String title);

}
