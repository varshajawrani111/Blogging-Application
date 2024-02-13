package com.codewithvarsha.blogapp2.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithvarsha.blogapp2.entities.Category;
import com.codewithvarsha.blogapp2.entities.Post;
import com.codewithvarsha.blogapp2.entities.User;
import com.codewithvarsha.blogapp2.exceptions.ResourceNotFoundException;
import com.codewithvarsha.blogapp2.payloads.PostDto;
import com.codewithvarsha.blogapp2.payloads.PostResponse;
import com.codewithvarsha.blogapp2.repositories.CategoryRepo;
import com.codewithvarsha.blogapp2.repositories.PostRepo;
import com.codewithvarsha.blogapp2.repositories.UserRepo;
import com.codewithvarsha.blogapp2.services.PostServices;

@Service
public class PostServiceImpl implements PostServices {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","id",userId));
		
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category" , "Category ID ", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());;
		post.setCategory(category);
		post.setUser(user);
		
		this.postRepo.save(post);
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post" , "Post ID ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		this.postRepo.save(post);
			
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post" , "Post ID ", postId));
		
		this.postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")){
			
			sort = Sort.by(sortBy).ascending();	
		}else {
			sort = Sort.by(sortBy).descending();	
		}

		//creating page able object
		Pageable p =PageRequest.of(pageNumber, pageSize,sort);
		
		Page<Post> pagePost = this.postRepo.findAll(p);
		
		List<Post> getAllPosts = pagePost.getContent();
		List<PostDto> getAllPostDtos = getAllPosts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		//to get the data related to pagination
		PostResponse postResponse = new PostResponse();
		
		postResponse.setContent(getAllPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSizeInteger(pagePost.getSize());
		postResponse.setTotalElementsInteger(pagePost.getTotalElements());
		postResponse.setTotalPagesInteger(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post" , "Post ID ", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategoryIdPost(Integer categoryId) {
		
		Category category = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category" , "Category ID ", categoryId));
		
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUserIdPost(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("User","id",userId));
		
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;

	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post) ->  this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
