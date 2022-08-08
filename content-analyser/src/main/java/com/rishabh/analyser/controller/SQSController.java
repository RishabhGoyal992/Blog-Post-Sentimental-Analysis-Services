package com.rishabh.analyser.controller;

import javax.print.event.PrintJobAttributeEvent;

import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rishabh.analyser.payload.PostSentimentDto;
import com.rishabh.analyser.payload.PostSentimentResponse;
import com.rishabh.analyser.service.PostSentimentService;

@RestController
@RequestMapping("/api/postsentiment")
public class SQSController {

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;
	
	@Autowired
	private PostSentimentService postSentimentService;
	
	@Value("${cloud.aws.end-point.uri}")
	private String endPoint;
	
//	@GetMapping("/put/{msg}")
//	public void putMessagedToQueue(@PathVariable("msg") String message) {
//		queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(message).build());
//	}
	
	@SqsListener("blog-app-sqs-queue")
	public void loadMessagesFromQueue(String message) {
		JSONObject receivedJSON = new JSONObject(message);
		
		Integer postId = receivedJSON.getInt("PostId");
		String postTitle = receivedJSON.getString("PostTitle");
		
		PostSentimentDto postSentimentDto = new PostSentimentDto();
		postSentimentDto.setPostId(postId);
		postSentimentDto.setPostTitle(postTitle);
		
		postSentimentService.createPost(postSentimentDto);
	}
	
	@GetMapping("/postid/{id}")
	public ResponseEntity<PostSentimentDto> getPostSetimentByPostId(@PathVariable("id") Integer postId) {
		PostSentimentDto postSentimentDto = this.postSentimentService.getPost(postId);
		
		return new ResponseEntity<PostSentimentDto>(postSentimentDto, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<PostSentimentResponse> getAllPostSentiments() {
		PostSentimentResponse postSentimentResponse = this.postSentimentService.getAllPost();
		
		return new ResponseEntity<PostSentimentResponse>(postSentimentResponse, HttpStatus.OK);
	}

}
