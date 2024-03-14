// Import necessary classes and annotations for testing
package com.example.social.project.UserTestCases;

import com.example.social.project.ExceptionHandler.UserNotFoundException;
import com.example.social.project.UserController.UserController;
import com.example.social.project.model.User;
import com.example.social.project.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

// Use MockitoExtension for JUnit 5
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    // Mock the UserServiceImpl dependency
    @Mock
    private UserServiceImpl userServiceImplMock;

    // Inject mocks into UserController
    @InjectMocks
    private UserController userControllerMock;

    // Create a sample user for testing
    private User sampleUser;

    // Set up the sampleUser before each test
    @BeforeEach
    public void setUp() {
        // Create a sample user for testing
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setFirstName("nikhil");
        sampleUser.setLastName("tiwari");
        sampleUser.setEmail("nikhiltiwari@example.com");
        sampleUser.setPassword("password");
    }

    // Test the createUser method for successful user creation
    @Test
    public void testCreateUser_Success() {
        // Arrange: Mock the userServiceImplMock.createUser method to return the sampleUser when called with any User argument.
        when(userServiceImplMock.createUser(any())).thenReturn(sampleUser);

        // Act: Call the userControllerMock.createUser method with the sampleUser.
        ResponseEntity<?> response = userControllerMock.createUser(sampleUser);

        // Assert:
        // Check that the response status is HttpStatus.CREATED.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Check that the response body is the same as the sampleUser.
        assertEquals(sampleUser, response.getBody());
        // Verify that the userServiceImplMock.createUser method was called with the sampleUser.
        verify(userServiceImplMock,times(1)).createUser(sampleUser);
    }

    // Test the createUser method for handling invalid user data
    @Test
    public void testCreateUser_InvalidData() {
        // Arrange: Create an invalidUser with missing required fields.
        User invalidUser = new User();
        // Mock the userServiceImplMock.createUser method to throw an IllegalArgumentException.
        when(userServiceImplMock.createUser(invalidUser)).thenThrow(new IllegalArgumentException("Invalid user data"));

        // Act: Call the userControllerMock.createUser method with the invalidUser.
        ResponseEntity<?> responseEntity = userControllerMock.createUser(invalidUser);

        // Assert:
        // Check that the response status is HttpStatus.BAD_REQUEST.
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // Check that the response body contains the expected error message.
        assertEquals("Invalid user data", responseEntity.getBody());
        // Verify that the userServiceImplMock.createUser method was called once with the invalidUser.
        verify(userServiceImplMock, times(1)).createUser(invalidUser);
    }

    // Test the getAllUsers method
    @Test
    public void testGetAllUsers() {
        // Arrange: Mock the userServiceImplMock.getAllUsers method to return a list containing the sampleUser.
        List<User> users = new ArrayList<>();
        users.add(sampleUser);
        when(userServiceImplMock.getAllUsers()).thenReturn(users);

        // Act: Call the userControllerMock.getAllUsers method.
        ResponseEntity<?> response = userControllerMock.getAllUsers();

        // Assert:
        // Check that the response status is HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Check that the response body is a list containing the sampleUser.
        assertEquals(users, response.getBody());
        // Verify that the userServiceImplMock.getAllUsers method was called.
        verify(userServiceImplMock).getAllUsers();
    }

    // Test the getUserById method for successful user retrieval
    @Test
    public void testGetUserById_Success() throws Exception {
        // Arrange: Set up a valid userId and mock the userServiceImplMock.findUserById method to return the sampleUser.
        Long userId = 1L;
        when(userServiceImplMock.findUserById(userId)).thenReturn(sampleUser);

        // Act: Call the userControllerMock.getUserById method with the valid userId.
        ResponseEntity<?> response = userControllerMock.getUserById(userId);

        // Assert:
        // Check that the response status is HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Check that the response body is the same as the sampleUser.
        assertEquals(sampleUser, response.getBody());
        // Verify that the userServiceImplMock.findUserById method was called with the valid userId.
        verify(userServiceImplMock).findUserById(userId);
    }

    // Test the getUserById method for handling a user not found scenario
    @Test
    public void testGetUserById_UserNotFound() {
        // Arrange: Set up a non-existent userId and mock the userServiceImplMock.findUserById method to throw UserNotFoundException.
        Long userId = 10L;
        when(userServiceImplMock.findUserById(userId)).thenThrow(new UserNotFoundException("User with ID 10 not found"));

        // Act and Assert:
        // Check that calling userControllerMock.getUserById with a non-existent userId returns 404 Not Found.
        ResponseEntity<?> response = userControllerMock.getUserById(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 10 not found", response.getBody());

        // Verify that the userServiceImplMock.findUserById method was called with the non-existent userId.
        verify(userServiceImplMock).findUserById(userId);
    }

    // Test the updateUser method for successful user update
    @Test
    public void testUpdateUser_Success() throws Exception {
        // Arrange
        Long userId = 1L;
        User updatedUser = new User();
        updatedUser.setId(userId);
        updatedUser.setFirstName("UpdatedFirstName");
        when(userServiceImplMock.updateUser(any(), eq(userId))).thenReturn(updatedUser);

        // Act
        ResponseEntity<?> response = userControllerMock.updateUser(updatedUser, userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
        verify(userServiceImplMock).updateUser(updatedUser, userId);
    }

    // Test the update user method when user not found
    @Test
    public void testUpdateUser_UserNotFound() throws Exception {
        // Arrange
        Long userId = 10L;
        User updatedUser = new User();
        when(userServiceImplMock.updateUser(any(), eq(userId))).thenThrow(new UserNotFoundException("User with ID 10 not found for update"));

        // Act and Assert
        ResponseEntity<?> response = userControllerMock.updateUser(updatedUser, userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User with ID 10 not found for update", response.getBody());
        verify(userServiceImplMock).updateUser(updatedUser, userId);
    }


    // Test the followUser method for successful user following
    @Test
    public void testFollowUser_Success() throws Exception {
        // Arrange: Set up valid userIds and mock the userServiceImplMock.followUser method to return the sampleUser.
        Long userId1 = 1L;
        Long userId2 = 2L;
        when(userServiceImplMock.followUser(userId1, userId2)).thenReturn(sampleUser);

        // Act: Call the userControllerMock.followUser method with the valid userIds.
        ResponseEntity<?> response = userControllerMock.followUser(userId1, userId2);

        // Assert:
        // Check that the response status is HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Check that the response body is the same as the sampleUser.
        assertEquals(sampleUser, response.getBody());
        // Verify that the userServiceImplMock.followUser method was called with the valid userIds.
        verify(userServiceImplMock).followUser(userId1, userId2);
    }

    // Test the followUser method for handling a user not found scenario
    @Test
    public void testFollowUser_UserNotFound() throws Exception {
        // Arrange: Set up a valid userId1, a non-existent userId2, and mock the userServiceImplMock.followUser method to throw an exception.
        Long userId1 = 1L;
        Long userId2 = 999L;
        when(userServiceImplMock.followUser(userId1, userId2)).thenThrow(new Exception("User not found"));

        // Act and Assert:
        // Check that calling userControllerMock.followUser with a non-existent userId2 throws an exception.
        assertThrows(Exception.class, () -> userControllerMock.followUser(userId1, userId2));
        // Verify that the userServiceImplMock.followUser method was called with the valid userIds.
        verify(userServiceImplMock).followUser(userId1, userId2);
    }

    // Test the searchUser method
    @Test
    public void testSearchUser() {
        // Arrange: Set up a search query and mock the userServiceImplMock.searchUser method to return a list containing the sampleUser.
        String query = "nikhil";
        List<User> searchResults = new ArrayList<>();
        searchResults.add(sampleUser);
        when(userServiceImplMock.searchUser(query)).thenReturn(searchResults);

        // Act: Call the userControllerMock.searchUser method with the search query.
        ResponseEntity<?> response = userControllerMock.searchUser(query);

        // Assert:
        // Check that the response status is HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Check that the response body is a list containing the sampleUser.
        assertEquals(searchResults, response.getBody());
        // Verify that the userServiceImplMock.searchUser method was called with the search query.
        verify(userServiceImplMock).searchUser(query);
    }
}
