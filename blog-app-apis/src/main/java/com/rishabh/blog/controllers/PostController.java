package com.rishabh.blog.controllers;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.blog.config.ApplicationConstants;
import com.rishabh.blog.payloads.ApiResponse;
import com.rishabh.blog.payloads.PostDto;
import com.rishabh.blog.payloads.PostResponse;
import com.rishabh.blog.services.PostService;

import lombok.Value;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@org.springframework.beans.factory.annotation.Value("${cloud.aws.end-point.uri}")
	private String endPoint;
	
	//Create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId) {
		PostDto createPostDto = this.postService.createPost(postDto, categoryId, userId);
		
		JSONObject jsonPayload = new JSONObject();
		
		jsonPayload.put("PostId", createPostDto.getPostId());
		jsonPayload.put("PostTitle", createPostDto.getPostTitle());
		
//		String payload = "PostId" + " : " + createPostDto.getPostId() + " , " + "PostTitle" + " : " + createPostDto.getPostTitle();
		
		queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(jsonPayload).build());
		
		return new ResponseEntity<PostDto>(createPostDto, HttpStatus.CREATED);
	}
	
	
	//----->>> Implement Pagenation here
	//Get by User-ID
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Integer userId) {
		List<PostDto> listPostDtoByUser = this.postService.getAllPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(listPostDtoByUser, HttpStatus.OK);
	}
	
	//----->>> Implement Pagenation here
	//Get by Category-ID
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategoryId(@PathVariable Integer categoryId) {
		List<PostDto> listPostDtoByCategory = this.postService.getAllPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(listPostDtoByCategory, HttpStatus.OK);
	}

//	
//	//---->>> Using constants here is a good practice instead of hard coding as it makes it scalable if changes need to be made across 100 places
//	//Get All posts	

	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			  @RequestParam(value = "pageNumber", defaultValue = ApplicationConstants.PAGE_NUMBER, required = false) Integer pageNumber
			, @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.PAGE_SIZE, required = false) Integer pageSize
			, @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.SORT_BY, required = false) String sortBy
			, @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.SORT_DIR, required = false) String sortDir) {
		
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	//Get 1 post by Id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPost(postId);
		
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer postId) {
		this.postService.deleltePost(postId);
		
		ApiResponse apiResponse = new ApiResponse("Deleted Successfully", true);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}
	
	//Search by Keyword
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPostListByKeyword(@PathVariable String keyword) {
		List<PostDto> resultDtos = this.postService.searchPosts(keyword);
		
		return new ResponseEntity<List<PostDto>>(resultDtos, HttpStatus.OK);
	}
}
