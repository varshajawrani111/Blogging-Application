package com.codewithvarsha.blogapp2.services;

import java.util.List;

import com.codewithvarsha.blogapp2.payloads.PostDto;
import com.codewithvarsha.blogapp2.payloads.PostResponse;


public interface PostServices {
	
	//create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update post
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete post
	void deletePost(Integer postId);
	
	//get all posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir);
	
	//get post by postId
	PostDto getPostById(Integer postId);
	
	//get posts by category id
	List<PostDto> getPostByCategoryIdPost(Integer categoryId);
	
	//get posts by user id
	List<PostDto> getPostByUserIdPost(Integer userId);
	
	//search posts
	List<PostDto> searchPosts(String keyword);

}
