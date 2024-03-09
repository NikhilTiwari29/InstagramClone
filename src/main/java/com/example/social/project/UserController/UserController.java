package com.example.social.project.UserController;

import com.example.social.project.model.User;
import com.example.social.project.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userServiceImpl.createUser(user);
            return (createdUser != null)
                    ? new ResponseEntity<>(createdUser, HttpStatus.CREATED)
                    : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
           throw e;
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> allUsers = userServiceImpl.getAllUsers();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        } catch (Exception e) {
           throw e;
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) throws Exception {
        try {
            User userById = userServiceImpl.findUserById(userId);
            return new ResponseEntity<>(userById, HttpStatus.OK);
        } catch (Exception e) {
           throw e;
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId) throws Exception {
        try {
            User updatedUser = userServiceImpl.updateUser(user, userId);
            return new ResponseEntity<>(updatedUser, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
           throw e;
        }
    }

    @PutMapping("/follow-unfollow/{userId1}/{userId2}")
    public ResponseEntity<?> followUser(@PathVariable Long userId1, @PathVariable Long userId2) throws Exception {
        try {
            User followed = userServiceImpl.followUser(userId1, userId2);
            return new ResponseEntity<>(followed, HttpStatus.OK);
        } catch (Exception e) {
           throw e;
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchUser(@RequestParam String query) {
        try {
            List<User> users = userServiceImpl.searchUser(query);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
           throw e;
        }
    }
}
