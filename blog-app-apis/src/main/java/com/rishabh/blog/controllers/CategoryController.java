package com.rishabh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rishabh.blog.payloads.ApiResponse;
import com.rishabh.blog.payloads.CategoryDto;
import com.rishabh.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//Create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategoryDto = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createdCategoryDto, HttpStatus.CREATED);
	}
	
	//Update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer cid) {
		CategoryDto updatedCategoryDto = this.categoryService.updateCategory(categoryDto, cid);
		
		return new ResponseEntity<CategoryDto>(updatedCategoryDto, HttpStatus.OK);
	}
	
	//Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
		
		//If this Id does not exist then ResourceNotFound exception will be thrown from service layer
		//This will be caught in the GlobalExceptionHandler
		this.categoryService.deleteCategory(categoryId);
		
		ApiResponse apiResponse = new ApiResponse("Deleted Successfully", true); 
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	//Get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
		CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
		
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	
	//Get All
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory() {
		List<CategoryDto> categoryDtoList = this.categoryService.getAllCategory();
		
		return new ResponseEntity<List<CategoryDto>>(categoryDtoList, HttpStatus.OK);
	}
	
}
