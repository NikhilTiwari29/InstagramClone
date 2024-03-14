package com.example.social.project.service;

import com.example.social.project.ExceptionHandler.UserNotFoundException;
import com.example.social.project.Repository.UserRepository;
import com.example.social.project.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (isValidUser(user)) {
            User newUser = new User();
            newUser.setFirstName(user.getFirstName());
            newUser.setLastName(user.getLastName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());

            User savedUser = userRepository.save(newUser);
            return savedUser;
        } else {
            throw new IllegalArgumentException("Invalid user data");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = userRepository.findAll();
        return allUser;
    }

    @Override
    public User findUserById(Long userId) {
        Optional<User> userById = userRepository.findById(userId);
        return userById.orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found"));
    }
    @Override
    public User findUserByEmail(String email) throws Exception {
        try {
            User email1 = userRepository.findByEmail(email);
            return email1;
        }
        catch (Exception e){
            throw new Exception("User with email " + email + " not found");
        }
    }

    @Override
    public List<User> searchUser(String query) {
        try {
            List<User> users = userRepository.searchUser(query);
            return users;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User updateUser(User user, Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found for update"));

        // Update only the specified fields
        if (user.getFirstName() != null) {
            existingUser.setFirstName(user.getFirstName());
        }

        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }

        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }

        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }

        return userRepository.save(existingUser);
    }


    @Override
    public User followUser(Long followerId, Long followeeId) {
        User follower = findUserById(followerId);
        User followee = findUserById(followeeId);

        if (follower.getFollowings().contains(followee.getId())){
            follower.getFollowings().remove(followee.getId());
            followee.getFollowers().remove(follower.getId());
        }
        else {
            follower.getFollowings().add(followee.getId());
            followee.getFollowers().add(follower.getId());
        }
        userRepository.save(follower);
        userRepository.save(followee);
        return follower;
    }

    private boolean isValidUser(User user) {
        return user.getFirstName() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getPassword() != null;
    }
}
