package com.example.social.project.service;

import com.example.social.project.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User findUserById(Long userId) throws Exception;
    User findUserByEmail(String email) throws Exception;

    List<User> searchUser(String query);
    User updateUser(User user,Long userId) throws Exception;

    User followUser(Long userId1,Long userId2) throws Exception;
}
