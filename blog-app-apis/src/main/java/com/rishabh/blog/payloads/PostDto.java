package com.rishabh.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.rishabh.blog.entities.Category;
import com.rishabh.blog.entities.Comment;
import com.rishabh.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String postTitle;
	private String postContent;
	
	//Remember DTO is not same as entity, it is made as per our needs, may be less, may be more. Not applicable here
	
	//->>>>will be added as default in service
	private String postImageName;
//	
	//->>>>All the bellow three will be added in service
	private Date postAddDate;

	private CategoryDto category;
	
	private UserDto user;
	
	//Use CommentDto to avoid recursive JSON
	private Set<CommentDto> comments = new HashSet<>();
}
