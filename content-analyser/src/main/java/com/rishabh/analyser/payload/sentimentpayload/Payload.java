package com.rishabh.analyser.payload.sentimentpayload;

import java.util.List;

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
"input",
"input_type",
"steps"
})
public class Payload {
	@JsonProperty("input")
	private String input;
	@JsonProperty("input_type")
	private String inputType;
	@JsonProperty("steps")
	private List<Step> steps = null;
}
