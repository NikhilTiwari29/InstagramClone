package com.example.social.project.UserTestCases;

import com.example.social.project.Repository.UserRepository;
import com.example.social.project.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        // Mock the behavior of the repository method findById
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(new User()));

        // Mock the behavior of the repository method save
        when(userRepositoryMock.save(new User())).thenReturn(new User());
    }

    @Test
    public void testFindById() {
        // Arrange
        Long userId = 1L;

        // Act
        Optional<User> user = userRepositoryMock.findById(userId);

        // Assert
        assertThat(user).isPresent();
    }

    @Test
    public void testSave() {
        // Arrange
        User user = new User();

        // Act
        User savedUser = userRepositoryMock.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
    }
}
