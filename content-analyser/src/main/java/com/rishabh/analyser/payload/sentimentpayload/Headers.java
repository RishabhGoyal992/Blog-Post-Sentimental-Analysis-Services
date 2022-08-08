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
"api-key",
"content-type"
})
public class Headers {
	@JsonProperty("api-key")
	private String apiKey;
	@JsonProperty("content-type")
	private String contentType;
}
