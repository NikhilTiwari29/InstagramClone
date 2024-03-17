package com.example.social.project.PostTestCases;

import com.example.social.project.model.Post;
import com.example.social.project.model.User;
import com.example.social.project.repository.PostRepository;
import com.example.social.project.service.PostServiceImpl;
import com.example.social.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepositoryMock;

    @Mock
    private UserService userService;

    @InjectMocks
    private PostServiceImpl postServiceImpl;

    private Post samplePost;
    private User sampleUser;

    @BeforeEach
    public void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);

        samplePost = new Post();
        samplePost.setId(1L);
        samplePost.setCaption("Test Caption");
        samplePost.setImage("Test Image");
        samplePost.setVideo("Test Video");
        samplePost.setUser(sampleUser);
    }

    @Test
    public void testCreatePost_Success() throws Exception {
        // Mocking userService behavior to return a non-null user when findUserById is called with any Long argument
        when(userService.findUserById(anyLong())).thenReturn(sampleUser);

        // Mocking postRepository behavior
        when(postRepositoryMock.save(any(Post.class))).thenReturn(samplePost); // Mocking post creation


        Post createdPost = postServiceImpl.createPost(samplePost, sampleUser.getId());

        assertNotNull(createdPost);
        assertEquals(samplePost.getId(), createdPost.getId());
        assertEquals(samplePost.getCaption(), createdPost.getCaption());
        assertEquals(samplePost.getImage(), createdPost.getImage());
        assertEquals(samplePost.getVideo(), createdPost.getVideo());
        assertEquals(sampleUser, createdPost.getUser());

        verify(postRepositoryMock, times(1)).save(any(Post.class));
    }

    @Test
    public void testFindAllPosts_Success() {
        when(postRepositoryMock.findAll()).thenReturn(Collections.singletonList(samplePost));

        List<Post> posts = postServiceImpl.findAllPosts();

        assertNotNull(posts);
        assertEquals(1, posts.size());
        assertEquals(samplePost, posts.get(0));

        verify(postRepositoryMock, times(1)).findAll();
    }

    @Test
    public void testFindPostById_Success() {
        when(postRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(samplePost));

        Post postById = postServiceImpl.findPostById(samplePost.getId());

        assertEquals(samplePost, postById);

        verify(postRepositoryMock, times(1)).findById(any(Long.class));
    }
}
