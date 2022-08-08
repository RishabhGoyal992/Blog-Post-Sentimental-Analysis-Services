package com.rishabh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishabh.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
}
