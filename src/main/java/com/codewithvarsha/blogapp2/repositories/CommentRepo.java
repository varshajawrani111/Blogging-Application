package com.codewithvarsha.blogapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvarsha.blogapp2.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
