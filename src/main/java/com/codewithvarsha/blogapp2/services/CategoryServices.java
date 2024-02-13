package com.codewithvarsha.blogapp2.services;

import java.util.List;

import com.codewithvarsha.blogapp2.payloads.CategoryDto;

public interface CategoryServices{
	
	//create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategoryDto(CategoryDto categoryDto, Integer cateId);
	
	//delete
	void deleteCategory(Integer categId);
	
	//get all
	List<CategoryDto> getAllCategories();
	
	//get
	CategoryDto getCtCategoryById(Integer cateId);

}
