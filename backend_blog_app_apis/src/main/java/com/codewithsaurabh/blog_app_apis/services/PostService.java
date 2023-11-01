package com.codewithsaurabh.blog_app_apis.services;

import java.util.List;

import com.codewithsaurabh.blog_app_apis.payloads.PostDto;
import com.codewithsaurabh.blog_app_apis.payloads.PostResponse;

public interface PostService {
	// create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// get All post by category
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// get All post by user
	List<PostDto> getPostByUser(Integer userId);

	
	
	// delete post
	void deleteUser(Integer postId);

	// update post
	PostDto updatePost(PostDto postDto, Integer postId);

	// get single post
	PostDto getPostById(Integer postId);

	// get All Post By pageNumber, pageSize,sortBy,sortDir,seachBy
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir,String seachBy);

	List<PostDto> searchPosts(String keyword);

}
