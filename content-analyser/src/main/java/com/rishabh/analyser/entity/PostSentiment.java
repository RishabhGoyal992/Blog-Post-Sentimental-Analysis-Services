package com.rishabh.analyser.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="PostSentiment")
@Data
@NoArgsConstructor
public class PostSentiment {
	
	@Id
	private Integer postId;
	
	@Column(name="post_title", length = 100, nullable = false)
	private String postTitle;
	
	@Column(name="post_sentiment")
	private String postSentiment;
	
}