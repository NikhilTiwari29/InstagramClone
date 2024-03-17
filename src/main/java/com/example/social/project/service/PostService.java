package com.example.social.project.service;

import com.example.social.project.model.Post;

import java.util.List;

public interface PostService {
    Post createPost(Post post,Long userId) throws Exception;
    String deletePost(Long postId,Long userId) throws Exception;
    Post findPostByUserId(Long userId) throws Exception;
    Post findPostById(Long postId) throws Exception;
    List<Post> findAllPosts();
    Post savePost(Long postId,Long userId) throws Exception;
    Post likePost(Long postId,Long userId) throws Exception;

}
