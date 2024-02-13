package com.codewithvarsha.blogapp2.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithvarsha.blogapp2.entities.Category;
import com.codewithvarsha.blogapp2.exceptions.ResourceNotFoundException;
import com.codewithvarsha.blogapp2.payloads.CategoryDto;
import com.codewithvarsha.blogapp2.repositories.CategoryRepo;
import com.codewithvarsha.blogapp2.services.CategoryServices;

@Service
public class CategoryServiceImpl implements CategoryServices{
	
	@Autowired 
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(addCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategoryDto(CategoryDto categoryDto, Integer cateId) {
		
		Category category = this.categoryRepo.findById(cateId).
				orElseThrow(()-> new ResourceNotFoundException("Category" , "Category ID ", cateId));
		
		category.setCategoryName(categoryDto.getCategoryName());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(updatedcategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categId) {
		Category category = this.categoryRepo.findById(categId).
				orElseThrow(()-> new ResourceNotFoundException("Category" , "Category ID ", categId));
		
		this.categoryRepo.delete(category);
		
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = categories.stream().map((cat) -> 
		this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return categoryDtos;
	}

	@Override
	public CategoryDto getCtCategoryById(Integer cateId) {
		Category category = this.categoryRepo.findById(cateId).
				orElseThrow(()-> new ResourceNotFoundException("Category" , "Category ID ", cateId));
		
		return this.modelMapper.map(category, CategoryDto.class);
	}


}
