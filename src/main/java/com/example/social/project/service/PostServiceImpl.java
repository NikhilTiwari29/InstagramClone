package com.example.social.project.service;

import com.example.social.project.repository.PostRepository;
import com.example.social.project.repository.UserRepository;
import com.example.social.project.model.Post;
import com.example.social.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private UserService userService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Post createPost(Post post, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        if (post == null || post.getCaption() == null || post.getCaption().isEmpty()) {
            throw new IllegalArgumentException("Post data is incomplete");
        }

        // You can choose to allow either image or video to be null
        // Validate that at least one of them is present
        if (post.getImage() == null && post.getVideo() == null) {
            throw new IllegalArgumentException("Post must have either image or video");
        }

        Post newPost = new Post();
        newPost.setCaption(post.getCaption());
        newPost.setImage(post.getImage());
        newPost.setVideo(post.getVideo());
        newPost.setUser(user);
        newPost.setCreatedAt(LocalDateTime.now());

        return postRepository.save(newPost);
    }

    @Override
    public String deletePost(Long postId, Long userId) {
        Post post = findPostById(postId);
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("You can't delete another user's post");
        }

        postRepository.delete(post);
        return "Post deleted successfully";
    }

    @Override
    public Post findPostByUserId(Long userId) throws Exception {
        Post postByUserId = postRepository.findPostByUserId(userId);
        if (postByUserId == null) {
            throw new IllegalArgumentException("No posts found for user with ID " + userId);
        }
        return postByUserId;
    }


    @Override
    public Post findPostById(Long postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        return postOptional.orElseThrow(() -> new IllegalArgumentException("Post with ID " + postId + " not found"));
    }

    @Override
    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post savePost(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Post or User not found.");
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        if (!user.getSavedPosts().contains(post)) {
            user.getSavedPosts().add(post);
        } else {
            user.getSavedPosts().remove(post);
        }

        return userRepository.save(user).getSavedPosts().stream()
                .filter(savedPost -> savedPost.getId().equals(postId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Error retrieving saved post."));
    }

    @Override
    public Post likePost(Long postId, Long userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isEmpty() || optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Post or User not found.");
        }

        Post post = optionalPost.get();
        User user = optionalUser.get();

        if (!post.getLikedPost().contains(user)) {
            post.getLikedPost().add(user);
        } else {
            post.getLikedPost().remove(user);
        }

        return postRepository.save(post);
    }
}
