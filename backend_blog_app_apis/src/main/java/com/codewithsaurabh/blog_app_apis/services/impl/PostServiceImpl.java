package com.codewithsaurabh.blog_app_apis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.codewithsaurabh.blog_app_apis.entities.Category;
import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.entities.User;
import com.codewithsaurabh.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithsaurabh.blog_app_apis.payloads.PostDto;
import com.codewithsaurabh.blog_app_apis.payloads.PostResponse;
import com.codewithsaurabh.blog_app_apis.repositories.CategoryRepo;
import com.codewithsaurabh.blog_app_apis.repositories.PostRepo;
import com.codewithsaurabh.blog_app_apis.repositories.UserRepo;
import com.codewithsaurabh.blog_app_apis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", "0"));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category id", "0"));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		System.err.println("Call in createPost!!!user getUserId- "+user.getUserId());
		post.setUser(user);
		post.setCategory(category);
		Post savePost = this.postRepo.save(post);
		
		return this.modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "Category id", categoryId.toString()));

		List<Post> posts = this.postRepo.findByCategory(cat);

		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return postDtos;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		System.err.println("PostServiceImpl -> getPostByUser  userId - "+userId);
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId.toString()));
		
		List<Post> postList = this.postRepo.findByUser(user);

		List<PostDto> postDtos = postList.stream()
				.map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		System.err.println("PostServiceImpl -> List<PostDto>  postList.get(0).getImageName() - "
				+postDtos.get(0).getImageName()+"   postDtos.getTitle - "+postDtos.get(0).getTitle()+"  postDtos.size = "+postDtos.size());
		
		return postDtos;
	}

	@Override /* Pagging in SprinBoot */
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDir,String seachBy) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc") || sortDir.equalsIgnoreCase("ASC")) {
			sort = Sort.by(sortBy).ascending();
		}else {
			sort = Sort.by(sortBy).descending();
		}
		
		Pageable p = PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtoList = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
									.collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setFirstPage(pagePost.isFirst());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public void deleteUser(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		this.postRepo.delete(post);
	}
	
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				    .orElseThrow(() -> new ResourceNotFoundException("Post","Post id",postId.toString()));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post Id", postId.toString()));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> searchPosts(String keyword){  
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}