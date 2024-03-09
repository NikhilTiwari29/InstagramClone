package com.example.social.project.service;

import com.example.social.project.Repository.PostRepository;
import com.example.social.project.Repository.UserRepository;
import com.example.social.project.model.Post;
import com.example.social.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{

    @Autowired
    UserService userService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Post createPost(Post post, Long userId) throws Exception {
        User user = userService.findUserById(userId);
        Post post1 = new Post();
        post1.setCaption(post.getCaption());
        post1.setImage(post.getImage());
        post1.setVideo(post.getVideo());
        post1.setUser(user);
        post1.setCreatedAt(LocalDateTime.now());
        Post savedPost = postRepository.save(post1);
        return savedPost;
    }

    @Override
    public String deletePost(Long postId, Long userId) throws Exception {
        Post postById = findPostById(postId);
        User userById = userService.findUserById(userId);
        if (postById.getUser().getId()!=userById.getId()){
            throw new Exception("You can't delete another user post");
        }
        postRepository.delete(postById);
        return "Post deleted successfully";
    }

    @Override
    public Post findPostByUserId(Long userId) throws Exception {
        Post postByUserId = postRepository.findPostByUserId(userId);
        return postByUserId;
    }

    @Override
    public Post findPostById(Long postId) throws Exception {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new Exception("Post with ID " + postId + " not found");
        }
        return postOptional.get();
    }


    @Override
    public List<Post> findAllPost() {
        List<Post> allPost = postRepository.findAll();
        return allPost;
    }

    @Override
    public Post savePost(Long postId, Long userId) throws Exception {
        // Step 1: Retrieve the Post and User entities
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();

            // Step 2: Add the post to the savedPosts list in the User entity
            if (!user.getSavedPosts().contains(post)) {
                user.getSavedPosts().add(post);
            } else {
                user.getSavedPosts().remove(post);
            }

            // Step 3: Save the updated User entity and returns the saved post
            return userRepository.save(user).getSavedPosts().stream()
                    .filter(savedPost -> savedPost.getId().equals(postId))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Error retrieving saved post."));
        } else {
            throw new Exception("Post or User not found.");
        }
    }

    @Override
    public Post likePost(Long postId, Long userId) throws Exception {
        // Step 1: Retrieve the Post and User entities
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            User user = optionalUser.get();

            // Step 2: Add the user to the likedPost list in the Post entity
            if (!post.getLikedPost().contains(user)) {
                post.getLikedPost().add(user);
            } else {
                post.getLikedPost().remove(user);
            }

            // Step 3: Save the updated Post entity
            return postRepository.save(post);
        } else {
            throw new Exception("Post or User not found.");
        }
    }
}
