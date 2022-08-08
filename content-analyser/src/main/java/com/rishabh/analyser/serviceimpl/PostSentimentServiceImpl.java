package com.rishabh.analyser.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.rishabh.analyser.configuration.ApplicationConstants;
import com.rishabh.analyser.entity.PostSentiment;
import com.rishabh.analyser.exception.ResourceNotFoundException;
import com.rishabh.analyser.payload.PostSentimentDto;
import com.rishabh.analyser.payload.PostSentimentResponse;
import com.rishabh.analyser.payload.sentimentpayload.Headers;
import com.rishabh.analyser.payload.sentimentpayload.Payload;
import com.rishabh.analyser.payload.sentimentpayload.SentimentRequest;
import com.rishabh.analyser.payload.sentimentpayload.Step;
import com.rishabh.analyser.repository.PostSentimentRepo;
import com.rishabh.analyser.service.PostSentimentService;


@Service
public class PostSentimentServiceImpl implements PostSentimentService {

	@Autowired
	private PostSentimentRepo postSentimentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public PostSentimentDto createPost(PostSentimentDto postSentimentDto) {
		
		PostSentiment postSentiment = this.modelMapper.map(postSentimentDto, PostSentiment.class);
		
		String sentimentString = this.getSentimentOfPost(postSentimentDto);
		
		System.out.println(sentimentString);
		
		postSentiment.setPostSentiment(sentimentString);
		
		PostSentiment savedPostSentiment = postSentimentRepo.save(postSentiment);
		
		return this.modelMapper.map(savedPostSentiment, PostSentimentDto.class);
	}

	@Override
	public PostSentimentDto getPost(Integer postId) {
		PostSentiment polledPostSentiment = postSentimentRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post Id Not Found = ",""+postId, 400));
	
		return this.modelMapper.map(polledPostSentiment, PostSentimentDto.class);
	}

	@Override
	public PostSentimentResponse getAllPost() {
		List<PostSentiment> polledPostSentimentsList = postSentimentRepo.findAll();
		
		List<PostSentimentDto> postSentimentDtos = new ArrayList<>();
		
		for(PostSentiment postSentiment : polledPostSentimentsList) {
			postSentimentDtos.add(this.modelMapper.map(postSentiment, PostSentimentDto.class));
		}
		
		PostSentimentResponse postSentimentResponse = new PostSentimentResponse();
		
		postSentimentResponse.setListPostSentimentDtos(postSentimentDtos);
		
		return postSentimentResponse;
	}
	
	public String getSentimentOfPost(PostSentimentDto postSentimentDto) {
		String payloadInputText = postSentimentDto.getPostTitle();
		String payloadInputType = "article";
		Step step = new Step("sentiments");
		
		List<Step> listSteps = List.of(step);
		
		Payload payload = new Payload(payloadInputText, payloadInputType, listSteps);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("api-key", ApplicationConstants.ONE_AI_API_KEY);
		httpHeaders.set("content-type", "application/json");
		
		HttpEntity<Payload> request = new HttpEntity<>(payload, httpHeaders);
		
		String responseSentimentString = "";
		try {
			responseSentimentString = restTemplate.exchange("https://api.oneai.com/api/v0/pipeline", HttpMethod.POST, request, String.class).getBody();
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(responseSentimentString);
		
		JSONObject responseSentimentJsonObject = new JSONObject(responseSentimentString);
		
		JSONArray sentimentOutputArray = responseSentimentJsonObject.getJSONArray("output");
		
		JSONObject sentimentOutputJsonObject = sentimentOutputArray.getJSONObject(0);
		
		JSONArray labelJsonArray = sentimentOutputJsonObject.getJSONArray("labels");
		
		if(labelJsonArray.isEmpty() == true) {
			return ApplicationConstants.DEFAULT_SENTIMENT;
		}
		
		JSONObject labelJsonObject = labelJsonArray.getJSONObject(0);
		
		String sentimentValueString = labelJsonObject.getString("value");
		
		if(sentimentValueString.equals(ApplicationConstants.POSITIVE_CONNOTATION) == true) {
			sentimentValueString = "POSITIVE";
		}
		else {
			sentimentValueString = "NEGATIVE";
		}
		
		return sentimentValueString;
	}
}
