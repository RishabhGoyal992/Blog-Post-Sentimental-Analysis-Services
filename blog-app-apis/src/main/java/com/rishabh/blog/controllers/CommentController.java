package com.rishabh.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.blog.payloads.ApiResponse;
import com.rishabh.blog.payloads.CommentDto;
import com.rishabh.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	//create
	@PostMapping("/user/{userId}/post/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer userId, @PathVariable Integer postId) {
		CommentDto createdCommentDto = this.commentService.createComment(commentDto, postId, userId);
		
		return new ResponseEntity<CommentDto>(createdCommentDto, HttpStatus.CREATED);
	}
	
	//delete
	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
		ApiResponse apiResponse = this.commentService.deleteComment(commentId);
		
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
}
