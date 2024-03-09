package com.example.social.project.service;

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
            return null;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUser = userRepository.findAll();
        return allUser;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> userById = userRepository.findById(userId);
        if (userById.isPresent()){
            return userById.get();
        }
        else {
            throw new Exception("User with ID " + userId + " not found");
        }
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
    public User updateUser(User user, Long userId) throws Exception {
        Optional<User> optionalExistingUser = userRepository.findById(userId);

        if (optionalExistingUser.isPresent()) {
            User existingUser = optionalExistingUser.get();

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

            User updatedUser = userRepository.save(existingUser);

            return updatedUser;
        } else {
            throw new Exception("User with ID " + userId + " not found for update");
        }
    }


    @Override
    public User followUser(Long userId1, Long userId2) throws Exception {
        return null;
    }


    private boolean isValidUser(User user) {
        return user.getFirstName() != null
                && user.getLastName() != null
                && user.getEmail() != null
                && user.getPassword() != null;
    }
}
