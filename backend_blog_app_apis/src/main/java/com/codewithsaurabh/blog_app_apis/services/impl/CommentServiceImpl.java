package com.codewithsaurabh.blog_app_apis.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithsaurabh.blog_app_apis.entities.Comment;
import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.exceptions.ResourceNotFoundException;
import com.codewithsaurabh.blog_app_apis.payloads.CommentDto;
import com.codewithsaurabh.blog_app_apis.repositories.CommentRepo;
import com.codewithsaurabh.blog_app_apis.repositories.PostRepo;
import com.codewithsaurabh.blog_app_apis.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId.toString()));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment saveComment = this.commentRepo.save(comment);

		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId.toString()));
		this.commentRepo.delete(comment);
	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId)
					.orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment id", commentId.toString()));

			comment.setContent(commentDto.getContent());

			Comment saveComment = this.commentRepo.save(comment);

			return this.modelMapper.map(saveComment, CommentDto.class);
		}
}
