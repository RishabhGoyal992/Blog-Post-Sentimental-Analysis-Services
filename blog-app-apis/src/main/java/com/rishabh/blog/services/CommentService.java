package com.rishabh.blog.services;

import com.rishabh.blog.payloads.ApiResponse;
import com.rishabh.blog.payloads.CommentDto;

public interface CommentService {
	
	//create
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);
	
	//delete
	public ApiResponse deleteComment(Integer commentId);
}
