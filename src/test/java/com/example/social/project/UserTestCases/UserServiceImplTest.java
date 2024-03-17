package com.example.social.project.UserTestCases;

import com.example.social.project.ExceptionHandler.UserNotFoundException;
import com.example.social.project.repository.UserRepository;
import com.example.social.project.model.User;
import com.example.social.project.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Create a sample user for testing
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setFirstName("nikhil");
        sampleUser.setLastName("tiwari");
        sampleUser.setEmail("nikhiltiwari@example.com");
        sampleUser.setPassword("password");
    }

    @Test
    void testFindUserById_UserFound() {
        // Arrange

        when(userRepositoryMock.findById(sampleUser.getId())).thenReturn(Optional.of(sampleUser));

        // Act
        User resultUser = userServiceImpl.findUserById(sampleUser.getId());

        // Assert
        assertNotNull(resultUser);
        assertEquals(sampleUser, resultUser);
    }

    @Test
    void testFindUserById_UserNotFound() {
        // Arrange
        Long userId = 2L;
        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.findUserById(userId));
    }

    @Test
    void testCreateUser_Success() {
        // Arrange

        when(userRepositoryMock.save(any())).thenReturn(sampleUser);

        // Act
        User createdUser = userServiceImpl.createUser(sampleUser);

        // Assert
        assertNotNull(createdUser);
        assertEquals(sampleUser.getFirstName(), createdUser.getFirstName());
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());

        when(userRepositoryMock.findAll()).thenReturn(users);

        // Act
        List<User> resultUsers = userServiceImpl.getAllUsers();

        // Assert
        assertNotNull(resultUsers);
        assertEquals(users.size(), resultUsers.size());
    }

    @Test
    void testUpdateUser_UserFound() {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("UpdatedFirstName");

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.of(new User()));
        when(userRepositoryMock.save(any())).thenReturn(updatedUser);

        // Act
        User resultUser = userServiceImpl.updateUser(updatedUser, userId);

        // Assert
        assertNotNull(resultUser);
        assertEquals(updatedUser.getFirstName(), resultUser.getFirstName());
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        Long userId = 2L;
        User updatedUser = new User();

        when(userRepositoryMock.findById(userId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.updateUser(updatedUser, userId));
    }

    @Test
    void testFollowUser_Success() {
        // Arrange
        Long userId1 = 1L;
        Long userId2 = 2L;
        User user1 = new User();
        user1.setId(userId1);
        User user2 = new User();
        user2.setId(userId2);

        when(userRepositoryMock.findById(userId1)).thenReturn(Optional.of(user1));
        when(userRepositoryMock.findById(userId2)).thenReturn(Optional.of(user2));

        // Act
        User resultUser = userServiceImpl.followUser(userId1, userId2);
        System.out.println("resultUser "+resultUser.getFollowings());

        // Assert
        assertNotNull(resultUser);
        assertTrue(resultUser.getFollowings().contains(user2.getId()));
        assertTrue(user2.getFollowers().contains(user1.getId()));
    }

    @Test
    void testFollowUser_UserNotFound() {
        // Arrange
        Long userId1 = 1L;
        Long userId2 = 2L;

        when(userRepositoryMock.findById(userId1)).thenReturn(Optional.of(new User()));
        when(userRepositoryMock.findById(userId2)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UserNotFoundException.class, () -> userServiceImpl.followUser(userId1, userId2));
    }

}
