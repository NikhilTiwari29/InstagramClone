package com.example.social.project.UserController;

import com.example.social.project.ExceptionHandler.UserNotFoundException;
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
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User userById = userServiceImpl.findUserById(userId);
            return ResponseEntity.ok(userById);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
        }
    }
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long userId) {
        try {
            User updatedUser = userServiceImpl.updateUser(user, userId);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: " + e.getMessage());
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
