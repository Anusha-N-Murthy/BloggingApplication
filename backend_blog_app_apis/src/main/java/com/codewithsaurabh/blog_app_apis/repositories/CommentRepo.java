package com.codewithsaurabh.blog_app_apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsaurabh.blog_app_apis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {

}
