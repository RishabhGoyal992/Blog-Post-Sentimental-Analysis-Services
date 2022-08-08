package com.rishabh.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.rishabh.blog.entities.Comment;
import com.rishabh.blog.entities.Post;
import com.rishabh.blog.entities.User;
import com.rishabh.blog.exceptions.ResourceNotFoundException;
import com.rishabh.blog.payloads.ApiResponse;
import com.rishabh.blog.payloads.CommentDto;
import com.rishabh.blog.repositories.CommentRepo;
import com.rishabh.blog.repositories.PostRepo;
import com.rishabh.blog.repositories.UserRepo;
import com.rishabh.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId));
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUserC(user);
		
		Comment savedComment = this.commentRepo.save(comment);
		
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public ApiResponse deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "Comment Id", commentId));
		
		this.commentRepo.delete(comment);
		
		ApiResponse apiResponse = new ApiResponse("Deleted Successfully", true);
		
		return apiResponse;
	}

}
