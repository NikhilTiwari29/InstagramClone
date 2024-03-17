package com.example.social.project.PostTestCases;

import com.example.social.project.UserController.PostController;
import com.example.social.project.model.Post;
import com.example.social.project.model.User;
import com.example.social.project.service.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostControllerTest {

    @Mock
    private PostServiceImpl postServiceImplMock;
    @InjectMocks
    private PostController postControllerMock;

    private Post samplePost;

    private User sampleUser;

    @BeforeEach
    public void setUp(){
        sampleUser = new User();
        samplePost = new Post();
        samplePost.setId(1L);
        samplePost.setCaption("Test Caption");
        samplePost.setImage("Test Image");
        samplePost.setVideo("Test Video");
        samplePost.setUser(sampleUser);
        samplePost.setCreatedAt(LocalDateTime.now());
    }

    @Test
    public void testCreatePost_Success() throws Exception {
        when(postServiceImplMock.createPost(any(),any())).
                thenReturn(samplePost);

        ResponseEntity<?> response = postControllerMock.createPost(samplePost, sampleUser.getId());

        assertEquals(HttpStatus.CREATED,response.getStatusCode());

        assertEquals(samplePost,response.getBody());

        verify(postServiceImplMock,times(1)).createPost(samplePost,sampleUser.getId());
    }

    @Test
    public void testCreatePost_Failure() throws Exception {
        Post inValidPost = new Post();

        when(postServiceImplMock.createPost(inValidPost,inValidPost.getId())).
                thenThrow(new IllegalArgumentException("Post data is incomplete"));

        ResponseEntity<?> response = postControllerMock.createPost(inValidPost, sampleUser.getId());

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

        assertEquals("Post data is incomplete", response.getBody());

        verify(postServiceImplMock,times(1)).createPost(inValidPost,sampleUser.getId());
    }

    @Test
    public void deletePost_Success() throws Exception {
        when(postServiceImplMock.deletePost(samplePost.getId(),sampleUser.getId())).
                thenReturn("Post deleted successfully");

        ResponseEntity<String> response = postControllerMock.
                deletePost(samplePost.getId(), sampleUser.getId());

        assertEquals(HttpStatus.OK,response.getStatusCode());

        assertEquals("Post deleted successfully",response.getBody());

        verify(postServiceImplMock,times(1)).
                deletePost(samplePost.getId(),sampleUser.getId());
    }

    @Test
    public void deletePost_Failure() throws Exception {
        when(postServiceImplMock.deletePost(samplePost.getId(),sampleUser.getId())).
                thenThrow(new IllegalArgumentException("You can't delete another user's post"));

        ResponseEntity<String> response = postControllerMock.
                deletePost(samplePost.getId(), sampleUser.getId());

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

        assertEquals("You can't delete another user's post",response.getBody());

        verify(postServiceImplMock,times(1)).
                deletePost(samplePost.getId(),sampleUser.getId());
    }

    @Test
    public void findPostById_Success() throws Exception {
        when(postServiceImplMock.findPostById(samplePost.getId())).
                thenReturn(samplePost);

        ResponseEntity<?> postById = postControllerMock.
                findPostById(samplePost.getId());

        assertEquals(HttpStatus.OK,postById.getStatusCode());

        assertEquals(samplePost,postById.getBody());

        verify(postServiceImplMock,times(1)).
                findPostById(samplePost.getId());
    }

    @Test
    public void findPostById_Failure() throws Exception {
        when(postServiceImplMock.findPostById(samplePost.getId())).
                thenThrow(new IllegalArgumentException("Post with ID " + samplePost.getId() + " not found"));

        ResponseEntity<?> postById = postControllerMock.
                findPostById(samplePost.getId());

        assertNotNull(postById);

        assertEquals(HttpStatus.BAD_REQUEST,postById.getStatusCode());

        assertEquals("Post with ID " + samplePost.getId() + " not found",postById.getBody());

        verify(postServiceImplMock,times(1)).
                findPostById(samplePost.getId());
    }

    @Test
    public void findPostByUserId_Success() throws Exception {
        when(postServiceImplMock.findPostByUserId(sampleUser.getId())).
                thenReturn(samplePost);

        ResponseEntity<?> postByUserId = postControllerMock.
                findPostByUserId(sampleUser.getId());

        assertEquals(HttpStatus.OK,postByUserId.getStatusCode());

        assertEquals(samplePost,postByUserId.getBody());

        verify(postServiceImplMock,times(1)).
                findPostByUserId(sampleUser.getId());
    }

    @Test
    public void findPostByUserId_Failure() throws Exception {
        when(postServiceImplMock.findPostByUserId(sampleUser.getId())).
                thenThrow(new IllegalArgumentException("No posts found for user with ID " + sampleUser.getId()));

        ResponseEntity<?> postByUserId = postControllerMock.
                findPostByUserId(sampleUser.getId());

        assertNotNull(postByUserId);

        assertEquals(HttpStatus.BAD_REQUEST,postByUserId.getStatusCode());

        assertEquals("No posts found for user with ID " + sampleUser.getId(),postByUserId.getBody());

        verify(postServiceImplMock,times(1)).
                findPostByUserId(sampleUser.getId());
    }

}
