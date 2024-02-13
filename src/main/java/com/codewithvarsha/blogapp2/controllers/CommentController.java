package com.codewithvarsha.blogapp2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvarsha.blogapp2.payloads.ApiResponse;
import com.codewithvarsha.blogapp2.payloads.CommentDto;
import com.codewithvarsha.blogapp2.services.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
		CommentDto createCommentDto = this.commentService.createComment(commentDto, postId);
		
		return new ResponseEntity<CommentDto>(createCommentDto,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Successfully deleted",false),HttpStatus.OK);
	}

}
