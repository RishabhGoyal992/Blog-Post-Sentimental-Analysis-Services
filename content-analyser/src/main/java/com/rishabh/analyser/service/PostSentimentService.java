package com.rishabh.analyser.service;

import com.rishabh.analyser.payload.PostSentimentDto;
import com.rishabh.analyser.payload.PostSentimentResponse;

public interface PostSentimentService {
	//create sentiment record for given post
	public PostSentimentDto createPost(PostSentimentDto postSentimentDto);

	//get
	public PostSentimentDto getPost(Integer postId);
	
	//get all
	public PostSentimentResponse getAllPost();
}
