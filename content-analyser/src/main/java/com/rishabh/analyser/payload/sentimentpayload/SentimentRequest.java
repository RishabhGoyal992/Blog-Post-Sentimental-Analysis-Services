package com.rishabh.analyser.payload.sentimentpayload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"headers",
"payload"
})
public class SentimentRequest {
	@JsonProperty("headers")
	private Headers headers;
	@JsonProperty("payload")
	private Payload payload;

}
