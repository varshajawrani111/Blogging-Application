package com.codewithvarsha.blogapp2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithvarsha.blogapp2.payloads.ApiResponse;
import com.codewithvarsha.blogapp2.payloads.CategoryDto;
import com.codewithvarsha.blogapp2.services.CategoryServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController{
	
	@Autowired
	private CategoryServices categoryServices;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto createcategory = this.categoryServices.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createcategory, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer catId){
		
		CategoryDto updatecategory = this.categoryServices.updateCategoryDto(categoryDto, catId);
		return new ResponseEntity<CategoryDto>(updatecategory, HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("catId") Integer catId){
		
		this.categoryServices.deleteCategory(catId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", false), HttpStatus.OK);
	}
	
	//get all
	@GetMapping("/allcategory")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		List<CategoryDto> categoryDtos = this.categoryServices.getAllCategories();	
		return ResponseEntity.ok(categoryDtos);
	}
	
	//get
	@GetMapping("/{catID}")
	public ResponseEntity<CategoryDto> getCategoryByID(@PathVariable("catID") Integer catId){
		
		CategoryDto categoryDto = this.categoryServices.getCtCategoryById(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}	

}
