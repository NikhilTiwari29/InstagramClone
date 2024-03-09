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
    public ResponseEntity<?> createPost(@RequestBody Post post,@PathVariable Long userId) throws Exception {
        try {
            Post createdPost = postServiceImpl.createPost(post, userId);
            return (createdPost != null)
                    ? new ResponseEntity<>(createdPost, HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping("/postId/{postId}/userId/{userId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId,@PathVariable Long userId) throws Exception {
          try {
              String message = postServiceImpl.deletePost(postId, userId);
              return new ResponseEntity<>(message,HttpStatus.OK);
          } catch (Exception e) {
              throw e;
          }
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> findPostByUserId(@PathVariable Long userId) throws Exception {
        try {
            Post postByUserId = postServiceImpl.findPostByUserId(userId);
            return new ResponseEntity<>(postByUserId, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/postId/{postId}")
    public ResponseEntity<?> findPostById(@PathVariable Long postId) throws Exception {
        try {
            Post postById = postServiceImpl.findPostById(postId);
            return new ResponseEntity<>(postById, HttpStatus.OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllPost() throws Exception {
        try {
            List<Post> allPost = postServiceImpl.findAllPost();
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
