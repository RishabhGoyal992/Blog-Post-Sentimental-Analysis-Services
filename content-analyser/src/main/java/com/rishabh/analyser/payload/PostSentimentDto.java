package com.rishabh.analyser.payload;

import java.sql.Date;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostSentimentDto {
	private Integer postId;
	
	private String postTitle;
	
	private String postSentiment;
}
