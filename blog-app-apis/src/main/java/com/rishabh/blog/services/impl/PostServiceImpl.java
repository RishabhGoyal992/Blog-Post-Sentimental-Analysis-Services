package com.rishabh.blog.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rishabh.blog.entities.Category;
import com.rishabh.blog.entities.Post;
import com.rishabh.blog.entities.User;
import com.rishabh.blog.exceptions.ResourceNotFoundException;
import com.rishabh.blog.payloads.PostDto;
import com.rishabh.blog.payloads.PostResponse;
import com.rishabh.blog.repositories.CategoryRepo;
import com.rishabh.blog.repositories.PostRepo;
import com.rishabh.blog.repositories.UserRepo;
import com.rishabh.blog.services.PostService;

import net.bytebuddy.asm.Advice.OffsetMapping.Sort;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		
		Post post = this.modelMapper.map(postDto, Post.class);
		
		post.setPostImageName("default.png");
		post.setPostAddDate(new Date());

		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		post.setUser(user);
		post.setCategory(category);
		
		Post newPost = this.postRepo.save(post);
		
		return this.modelMapper.map(newPost, PostDto.class);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deleltePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));

		this.postRepo.deleteById(postId);

		return;
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Post Id", postId));
		
		PostDto gotPostDto = this.modelMapper.map(post, PostDto.class);
		
		return gotPostDto;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
//		int pageSize = 5;
//		int pageNumber = 3;
		
		org.springframework.data.domain.Sort sort = null;
		
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = org.springframework.data.domain.Sort.by(sortBy).ascending();
		}
		else {
			sort = org.springframework.data.domain.Sort.by(sortBy).descending();
		}
		
		
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
//		List<Post> listOfAllPosts = this.postRepo.findAll(null);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		
		List<PostDto> listOfAllPostDtos = new ArrayList<>();
		
		for(Post p:pagePost) {
			listOfAllPostDtos.add(this.modelMapper.map(p, PostDto.class));
		}
		
		PostResponse postResponse = new PostResponse();
		
		postResponse.setListPostDtos(listOfAllPostDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		Category tofindCategory = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		List<Post> listPosts = this.postRepo.findByCategory(tofindCategory);
		
		List<PostDto> listPostDto = new ArrayList<>();
		
		for(Post p:listPosts) {
			listPostDto.add(this.modelMapper.map(p, PostDto.class));
		}
		
		return listPostDto;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		User toFindUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		
		List<Post> listPostByUser = this.postRepo.findByUser(toFindUser);
		
		List<PostDto> listPostDto = new ArrayList<>();
		
		for(Post p:listPostByUser) {
			listPostDto.add(this.modelMapper.map(p, PostDto.class));
		}
		
		return listPostDto;
	}
	
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> listPostByKeyword = this.postRepo.findBypostTitleContaining(keyword);
		
		List<PostDto> listPostDto = new ArrayList<>();
		
		for(Post p:listPostByKeyword) {
			listPostDto.add(this.modelMapper.map(p, PostDto.class));
		}
		
		return listPostDto;
	}

}
