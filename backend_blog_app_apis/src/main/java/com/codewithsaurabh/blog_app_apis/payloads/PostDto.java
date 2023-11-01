package com.codewithsaurabh.blog_app_apis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.codewithsaurabh.blog_app_apis.entities.Category;
import com.codewithsaurabh.blog_app_apis.entities.Comment;
import com.codewithsaurabh.blog_app_apis.entities.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	private String postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	private Category category;
	private String userId;
//    private User user;
    private Set<Comment> comments = new HashSet<>();
}
