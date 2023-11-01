package com.codewithsaurabh.blog_app_apis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithsaurabh.blog_app_apis.entities.Category;
import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.entities.User;
public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);
	
	
    // @Query("SELECT p FROM post p WHERE  p.title LIKE : key ")
	 List<Post> findByTitleContaining(String key);
}
