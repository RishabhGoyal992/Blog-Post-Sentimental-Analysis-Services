package com.rishabh.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Minimum size is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "Minimum size is 10")
	private String categoryDescription;

}
