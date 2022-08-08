package com.rishabh.analyser.payload;

import java.util.List;

import lombok.Data;

@Data
public class PostSentimentResponse {
	
	private List<PostSentimentDto> listPostSentimentDtos;
}
