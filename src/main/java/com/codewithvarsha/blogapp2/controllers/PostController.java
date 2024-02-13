package com.codewithvarsha.blogapp2.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codewithvarsha.blogapp2.config.AppConstants;
import com.codewithvarsha.blogapp2.payloads.ApiResponse;
import com.codewithvarsha.blogapp2.payloads.PostDto;
import com.codewithvarsha.blogapp2.payloads.PostResponse;
import com.codewithvarsha.blogapp2.services.PostServices;
import com.codewithvarsha.blogapp2.services.impl.FileService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/")
public class PostController {
	
	@Autowired
	private PostServices postServices;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId, @PathVariable Integer categoryId){
		
		PostDto createPost = this.postServices.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
	}
	
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){
		
		List<PostDto> postsDtos = this.postServices.getPostByUserIdPost(userId);
		
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
		
	}
	
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){
		
		List<PostDto> postsDtos = this.postServices.getPostByCategoryIdPost(categoryId);
		
		return new ResponseEntity<List<PostDto>>(postsDtos,HttpStatus.OK);
		
	}
	
	//get all posts
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
	@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
	@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,
	@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){			

		//pageNumber runs from 0
		PostResponse postsDtos = this.postServices.getAllPosts(pageNumber,pageSize,sortBy,sortDir);

		return new ResponseEntity<PostResponse>(postsDtos,HttpStatus.OK);
	}
	
	//get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){			
		PostDto postsDto = this.postServices.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postsDto,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		
		this.postServices.deletePost(postId);
		return new ApiResponse("Post is successfully deleted", true);		
	}
	
	//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatedPostDto = this.postServices.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPostDto,HttpStatus.OK);
	}
	
	//search post
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keyword){
		
		List<PostDto> resultDtos = this.postServices.searchPosts(keyword);
		return new ResponseEntity<List<PostDto>>(resultDtos,HttpStatus.OK);
	}
	
	//post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postServices.getPostById(postId);
		
		String fileName = this.fileService.uploadStringImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePostDto =  this.postServices.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatePostDto,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
		//frontend url to serve the image
		//http://localhost:9090/api/posts/image/ea0d2919-26bc-4417-9963-906447c7575e.jpg
		
	}

}
