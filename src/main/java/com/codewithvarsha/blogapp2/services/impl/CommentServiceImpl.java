package com.codewithvarsha.blogapp2.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvarsha.blogapp2.entities.Comment;
import com.codewithvarsha.blogapp2.entities.Post;
import com.codewithvarsha.blogapp2.exceptions.ResourceNotFoundException;
import com.codewithvarsha.blogapp2.payloads.CommentDto;
import com.codewithvarsha.blogapp2.repositories.CommentRepo;
import com.codewithvarsha.blogapp2.repositories.PostRepo;
import com.codewithvarsha.blogapp2.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).
				orElseThrow(()-> new ResourceNotFoundException("Post" , "Post ID ", postId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		this.commentRepo.save(comment);
		
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(
				()-> new ResourceNotFoundException("Comment" , "Comment ID ", commentId));
		
		this.commentRepo.delete(comment);

	}

}
