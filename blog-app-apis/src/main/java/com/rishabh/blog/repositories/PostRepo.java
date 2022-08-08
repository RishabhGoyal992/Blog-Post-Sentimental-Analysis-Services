package com.rishabh.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rishabh.blog.entities.Category;
import com.rishabh.blog.entities.Post;
import com.rishabh.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	//EXPLORE THESE Functions
	public List<Post> findByUser(User user);
	public List<Post> findByCategory(Category category);
	
	public List<Post> findBypostTitleContaining(String keyword);
	
//	//Can be used for prior Hibernate Versions where number query problem is there
////	@Query("select p from Post p where p.title like :key")
//	List<Post> searchByTitle(@Param("key") String title);
}
