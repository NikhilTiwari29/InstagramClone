package com.example.social.project.UserController;

import com.example.social.project.model.Post;
import com.example.social.project.service.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostServiceImpl postServiceImpl;

    @PostMapping("/createPost/user/{userId}")
    public ResponseEntity<?> createPost(@RequestBody Post post, @PathVariable Long userId) {
        try {
            Post createdPost = postServiceImpl.createPost(post, userId);
            return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/postId/{postId}/userId/{userId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
        try {
            String message = postServiceImpl.deletePost(postId, userId);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // IllegalArgumentException occurs when the user tries to delete another user's post
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Other exceptions are considered as internal server errors
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> findPostByUserId(@PathVariable Long userId) {
        try {
            Post postByUserId = postServiceImpl.findPostByUserId(userId);
            return new ResponseEntity<>(postByUserId, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/postId/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable Long postId) {
        try {
            Post postById = postServiceImpl.findPostById(postId);
            return new ResponseEntity<>(postById, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllPost() throws Exception {
        try {
            List<Post> allPost = postServiceImpl.findAllPosts();
            return new ResponseEntity<>(allPost, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/save/postId/{postId}/userId/{userId}")
    public ResponseEntity<?> savePost(@PathVariable Long postId,@PathVariable Long userId) throws Exception {
        try {
            Post post = postServiceImpl.savePost(postId, userId);
            return new ResponseEntity<>(post, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/like/postId/{postId}/userId/{userId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId,@PathVariable Long userId) throws Exception {
        try {
            Post likePost = postServiceImpl.likePost(postId, userId);
            return new ResponseEntity<>(likePost, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw e;
        }
    }
}
