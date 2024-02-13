package com.codewithvarsha.blogapp2.services;

import com.codewithvarsha.blogapp2.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);

}
