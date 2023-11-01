package com.codewithsaurabh.blog_app_apis.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codewithsaurabh.blog_app_apis.entities.Like;
import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.entities.User;



@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {
   Optional<Like> findByUserAndPost(User user, Post post);
   List<Like> findByUser(User user);
}
