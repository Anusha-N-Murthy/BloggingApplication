package com.codewithsaurabh.blog_app_apis.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.services.LikeService;

@RequestMapping("/api")
@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;
    @PostMapping("/like/{userId}/{postId}")
    public ResponseEntity<String> likePost(@PathVariable int userId,@PathVariable Integer postId)
    {
        likeService.likePost(userId,postId);
        return ResponseEntity.ok("post liked successfully");
    }
    @PostMapping("/unlike/{userId}/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable int userId,@PathVariable Integer postId)
    {
        likeService.likePost(userId,postId);
        return ResponseEntity.ok("post unliked successfully");
    }
    @GetMapping("/likedPost/{userId}")
    public ResponseEntity<List<Post>> getLikedPost(@PathVariable int userId)
    {
        List<Post> likedPosts = likeService.getLikedPosts(userId);
        return ResponseEntity.ok(likedPosts);
    }

}
