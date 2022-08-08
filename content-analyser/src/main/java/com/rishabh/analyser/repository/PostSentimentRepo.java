package com.rishabh.analyser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishabh.analyser.entity.PostSentiment;

public interface PostSentimentRepo extends JpaRepository<PostSentiment, Integer> {

}
