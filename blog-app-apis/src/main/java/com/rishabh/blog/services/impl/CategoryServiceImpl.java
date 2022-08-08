 package com.rishabh.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ResourceClosedException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rishabh.blog.entities.Category;
import com.rishabh.blog.exceptions.ResourceNotFoundException;
import com.rishabh.blog.payloads.CategoryDto;
import com.rishabh.blog.repositories.CategoryRepo;
import com.rishabh.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCategory = this.categoryRepo.save(category);
		
		return this.modelMapper.map(addedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category currentCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		currentCategory.setCategoryTitle(categoryDto.getCategoryTitle());
		currentCategory.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedCategory = this.categoryRepo.save(currentCategory);
		
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		//this is done to ensure that the inputed category ID exists. We could have deleted directly without fetching class, but that would have not helped us in cathing this false case
		Category currentCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		this.categoryRepo.deleteById(categoryId);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category askedCategory = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		return this.modelMapper.map(askedCategory, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategory = this.categoryRepo.findAll();
		
		List<CategoryDto> allCategoryDto = new ArrayList<>();
		
		for(Category cat:allCategory) {
			allCategoryDto.add(this.modelMapper.map(cat, CategoryDto.class));
		}
		
		return allCategoryDto;
	}

}
