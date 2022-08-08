 package com.rishabh.blog.services;

import java.util.List;

import com.rishabh.blog.payloads.PostDto;
import com.rishabh.blog.payloads.PostResponse;

public interface PostService {
	//create
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);
	
	//update
	public PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	public void deleltePost(Integer postId);
	
	//get
	public PostDto getPost(Integer postId);
	
	//get all
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy,  String sortDir);
	
	//get all post by category
	public List<PostDto> getAllPostByCategory(Integer categoryId);
	
	//get all post by user
	public List<PostDto> getAllPostByUser(Integer userId);
	
	//Search Post by Keyword
	public List<PostDto> searchPosts(String keyword);
}
