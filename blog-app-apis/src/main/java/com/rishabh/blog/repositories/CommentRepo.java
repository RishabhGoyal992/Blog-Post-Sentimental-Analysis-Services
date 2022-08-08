package com.rishabh.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rishabh.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
