package com.codewithsaurabh.blog_app_apis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codewithsaurabh.blog_app_apis.entities.Like;
import com.codewithsaurabh.blog_app_apis.entities.Post;
import com.codewithsaurabh.blog_app_apis.entities.User;
import com.codewithsaurabh.blog_app_apis.repositories.LikeRepo;
import com.codewithsaurabh.blog_app_apis.repositories.PostRepo;
import com.codewithsaurabh.blog_app_apis.repositories.UserRepo;
import com.codewithsaurabh.blog_app_apis.services.LikeService;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepo likeRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;

    @Override
    public void likePost(int userId, Integer postId) {
        User user = userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("user not found"));
        Post post = postRepo.findById(postId).orElseThrow(()->new EntityNotFoundException("post not found"));
        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepo.save(like);


    }

    @Override
    public void unlikePost(int userId, Integer postId) {
        User user = userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("user not found"));
        Post post = postRepo.findById(postId).orElseThrow(()->new EntityNotFoundException("post not found"));
//        Optional<Like> likeOptional = likeRepo.findByUserAndPost(user,post);
        java.util.Optional<Like> likeOptional=likeRepo.findByUserAndPost(user, post);
        likeOptional.ifPresent(like ->likeRepo.delete(like));

    }

    @Override
    public List<Post> getLikedPosts(int userId) {
        User user = userRepo.findById(userId).orElseThrow(()->new EntityNotFoundException("user not found"));
        List<Like> likes = likeRepo.findByUser(user);
        List<Post> likedPosts = likes.stream()
                .map(Like::getPost)
                .collect(Collectors.toList());
        return likedPosts;
    }


}


