package com.codewithsaurabh.blog_app_apis.services;

import java.util.List;

import com.codewithsaurabh.blog_app_apis.entities.Post;

public interface LikeService {
    void likePost(int userId,Integer postId);
    void unlikePost(int userId,Integer postId);
    List<Post> getLikedPosts(int userId);
}