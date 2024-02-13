package com.codewithvarsha.blogapp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithvarsha.blogapp2.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
