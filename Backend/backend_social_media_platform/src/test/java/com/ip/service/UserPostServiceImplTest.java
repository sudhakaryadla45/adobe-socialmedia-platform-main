package com.ip.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ip.dto.PostDTO;
import com.ip.dto.PostDTOV1;
import com.ip.dto.UserDTO;
import com.ip.dto.UserDTOV1;
import com.ip.exception.PostException;
import com.ip.exception.UserException;
import com.ip.model.Post;
import com.ip.model.User;
import com.ip.repository.PostRepo;
import com.ip.repository.UserRepo;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserPostServiceImplTest {

	@Mock
    private UserRepo userRepo;
	
	@Mock
	private PostRepo postRepo;

    @InjectMocks
    private UserPostServiceImpl userService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setName("Indrajit Paul");
        userDTO.setEmail("indrajit@gmail.com");
        userDTO.setBio("Hey! I am a passionate software developer from Kolkata");
    }

    @Test
    void testCreateUser() throws UserException {
    	
        User user = new User();
        user.setId(1);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setBio(userDTO.getBio());

        when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userRepo.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(userDTO);

        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getEmail(), result.getEmail());
        assertEquals(userDTO.getBio(), result.getBio());

        verify(userRepo, times(1)).findByEmail(any(String.class));
        verify(userRepo, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUserThrowsException() {
        when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.of(new User()));

        assertThrows(UserException.class, () -> {
            userService.createUser(userDTO);
        });

        verify(userRepo, times(1)).findByEmail(any(String.class));
        verify(userRepo, never()).save(any(User.class));
    }
	
	
	//******************************************************************************
	
	
    @Test
	public void testGetUserWithValidId() throws UserException {
		User user = new User();
		user.setId(1);
		user.setName("Indrajit Paul");
		user.setEmail("indrajit@gmail.com");

		when(userRepo.findById(1)).thenReturn(Optional.of(user));

		User result = userService.getUser(1);

		Assertions.assertEquals(user, result);
	}

	@Test
	public void testGetUserWithInvalidId() {
		when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

		Assertions.assertThrows(UserException.class, () -> {
			userService.getUser(999);
		});
	}
	
	
    
    //**************************************************************************
	
	
	@Test
	void testUpdateUser() throws UserException {
	
		User existingUser = new User();
		existingUser.setId(1);
		existingUser.setName("Indrajit Paul");
		existingUser.setBio("Hello, I'm Indrajit");
		existingUser.setCreatedAt(LocalDateTime.now().minusDays(1));
		
		UserDTOV1 dto = new UserDTOV1();
		dto.setName("Indrajit Paul");
		dto.setBio("Hello, I'm Indrajit");
		
		when(userRepo.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
		when(userRepo.save(any(User.class))).thenReturn(existingUser);
		

		User updatedUser = userService.updateUser(existingUser.getId(), dto);
		

		assertNotNull(updatedUser);
		assertEquals(existingUser.getId(), updatedUser.getId());
		assertEquals(dto.getName(), updatedUser.getName());
		assertEquals(dto.getBio(), updatedUser.getBio());
		assertNotNull(updatedUser.getUpdatedAt());
		
		verify(userRepo, times(1)).findById(existingUser.getId());
		verify(userRepo, times(1)).save(existingUser);
	}
	
	@Test
	void testUpdateUserWithInvalidUserId() {

		Integer invalidUserId = 999;
		UserDTOV1 dto = new UserDTOV1();
		dto.setName("Indrajit Paul");
		dto.setBio("Hello, I'm Indrajit Paul");
		
		when(userRepo.findById(invalidUserId)).thenReturn(Optional.empty());
		

		assertThrows(UserException.class, () -> userService.updateUser(invalidUserId, dto));
		
		verify(userRepo, times(1)).findById(invalidUserId);
	}
	
	@Test
	void testUpdateUserWithNullNameAndBio() {

		User existingUser = new User();
		existingUser.setId(1);
		existingUser.setName("Indrajit Paul");
		existingUser.setBio("Hello, I'm Indrajit Paul");
		existingUser.setCreatedAt(LocalDateTime.now().minusDays(1));
		
		UserDTOV1 dto = new UserDTOV1();
		
		when(userRepo.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
		

		assertThrows(UserException.class, () -> userService.updateUser(existingUser.getId(), dto));
		
		verify(userRepo, times(1)).findById(existingUser.getId());
	}
	
	
	
	//***********************************************************************
    
	
	@Test
    void testDeleteUser() throws UserException {

        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        Optional<User> optionalUser = Optional.of(user);

        when(userRepo.findById(userId)).thenReturn(optionalUser);


        User deletedUser = userService.deleteUser(userId);


        verify(userRepo, times(1)).delete(user);
        assertEquals(userId, deletedUser.getId());
    }

    @Test
    void testDeleteUserWithInvalidUserId() {

        Integer userId = 1;
        Optional<User> optionalUser = Optional.empty();

        when(userRepo.findById(userId)).thenReturn(optionalUser);


        assertThrows(UserException.class, () -> userService.deleteUser(userId));
        verify(userRepo, never()).delete(any(User.class));
    }
    
    
	
	//***********************************************************************
	
    @BeforeEach
    void setUp1() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTotalNumberOfUsers() throws UserException {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        when(userRepo.findAll()).thenReturn(users);
        
        assertEquals(3, userService.getTotalNumberOfUsers());
    }
    
    @Test
    void testGetTotalNumberOfUsersNoUsers() throws UserException {
        List<User> users = new ArrayList<>();
        when(userRepo.findAll()).thenReturn(users);
        
        UserException exception = assertThrows(UserException.class, () -> {
            userService.getTotalNumberOfUsers();
        });
        
        assertEquals("No user found", exception.getMessage());
    }
    
    
    //*************************************************************************
    

    @Test
    void testGetTop5ActiveUsers() throws UserException {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("John");
        user1.setEmail("john@gmail.com");
        user1.setBio("Test bio");
        user1.setCreatedAt(LocalDateTime.now());
        user1.setUpdatedAt(LocalDateTime.now());
        userList.add(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setName("Jane");
        user2.setEmail("jane@gmail.com");
        user2.setBio("Test bio");
        user2.setCreatedAt(LocalDateTime.now());
        user2.setUpdatedAt(LocalDateTime.now());
        userList.add(user2);

        Mockito.when(userRepo.findAll()).thenReturn(userList);

        List<User> top5Users = userService.getTop5ActiveUsers();

        assertNotNull(top5Users);
        assertEquals(2, top5Users.size());

        User userWithMostPosts = top5Users.get(0);
        assertEquals("John", userWithMostPosts.getName());

        User userWithSecondMostPosts = top5Users.get(1);
        assertEquals("Jane", userWithSecondMostPosts.getName());
    }

    @Test
    void testGetTop5ActiveUsersNoUsers() {
        Mockito.when(userRepo.findAll()).thenReturn(Collections.emptyList());

        UserException exception = assertThrows(UserException.class, () -> {
            userService.getTop5ActiveUsers();
        });

        assertEquals("No user found", exception.getMessage());
    }
    
    
    //***************************************************************************
    
    
    @Test
	public void testCreatePost_validInput_shouldCreatePost() throws UserException {

		Integer userId = 1;
		User user = new User();
		user.setId(userId);
		PostDTO postDTO = new PostDTO();
		postDTO.setUserId(userId);
		postDTO.setContent("Test post content");
		
		when(userRepo.findById(userId)).thenReturn(Optional.of(user));
		when(postRepo.save(any(Post.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
		

		Post createdPost = userService.createPost(postDTO);
		

		assertNotNull(createdPost);
		assertEquals(postDTO.getContent(), createdPost.getContent());
		assertEquals(0, createdPost.getLikes());
		assertEquals(user, createdPost.getUser());
		assertEquals(1, user.getPosts().size());
		verify(userRepo, times(1)).findById(userId);
		verify(postRepo, times(1)).save(any(Post.class));
	}
	
	@Test
	public void testCreatePost_invalidUserId_shouldThrowException() throws UserException {

		Integer userId = 1;
		PostDTO postDTO = new PostDTO();
		postDTO.setUserId(userId);
		postDTO.setContent("Test post content");
		
		when(userRepo.findById(userId)).thenReturn(Optional.empty());
		

		assertThrows(UserException.class, () -> userService.createPost(postDTO));
		verify(userRepo, times(1)).findById(userId);
		verify(postRepo, never()).save(any(Post.class));
	}


	//*******************************************************************

	@Test
    void testGetPostValid() throws PostException {

        Post post = new Post();
        post.setId(1);
        Optional<Post> optionalPost = Optional.of(post);
        when(postRepo.findById(1)).thenReturn(optionalPost);


        Post result = userService.getPost(1);


        assertEquals(post, result);
        verify(postRepo, times(1)).findById(1);
    }

    @Test
    void testGetPostInvalid() {

        Optional<Post> optionalPost = Optional.empty();
        when(postRepo.findById(1)).thenReturn(optionalPost);


        assertThrows(PostException.class, () -> userService.getPost(1));
        verify(postRepo, times(1)).findById(1);
    }
	
    
    //***************************************************************************
	
	
    @Test
    void testUpdatePostWithValidPostIdAndContent() throws PostException {

        PostDTOV1 dto = new PostDTOV1();
        dto.setPostId(1);
        dto.setContent("Updated content");

        Post post = new Post();
        post.setId(1);
        post.setContent("Original content");
        post.setCreated_at(LocalDateTime.now());
        post.setUpdated_at(null);
        post.setLikes(0);
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@test.com");
        post.setUser(user);

        when(postRepo.findById(1)).thenReturn(Optional.of(post));
        when(postRepo.save(post)).thenReturn(post);


        Post updatedPost = userService.updatePost(dto);

 
        assertEquals(1, updatedPost.getId());
        assertEquals("Updated content", updatedPost.getContent());
        assertNotNull(updatedPost.getUpdated_at());
        verify(postRepo, times(1)).findById(1);
        verify(postRepo, times(1)).save(post);
    }

    @Test
    void testUpdatePostWithInvalidPostId() {

        PostDTOV1 dto = new PostDTOV1();
        dto.setPostId(1);
        dto.setContent("Updated content");

        when(postRepo.findById(1)).thenReturn(Optional.empty());


        assertThrows(PostException.class, () -> userService.updatePost(dto));
        verify(postRepo, times(1)).findById(1);
        verify(postRepo, never()).save(any());
    }

    @Test
    void testUpdatePostWithNullContent() {

        PostDTOV1 dto = new PostDTOV1();
        dto.setPostId(1);
        dto.setContent(null);

        Post post = new Post();
        post.setId(1);
        post.setContent("Original content");
        post.setCreated_at(LocalDateTime.now());
        post.setUpdated_at(null);
        post.setLikes(0);
        User user = new User();
        user.setId(1);
        user.setName("Test User");
        user.setEmail("test@test.com");
        post.setUser(user);

        when(postRepo.findById(1)).thenReturn(Optional.of(post));


        assertThrows(PostException.class, () -> userService.updatePost(dto));
        verify(postRepo, times(1)).findById(1);
        verify(postRepo, never()).save(any());
    }
	
	
	//************************************************************************
	
    @Test
    void deletePost_shouldDeletePost_whenValidPostId() throws PostException {

        int postId = 1;
        Post post = new Post();
        post.setId(postId);
        when(postRepo.findById(postId)).thenReturn(Optional.of(post));


        Post deletedPost = userService.deletePost(postId);


        verify(postRepo, times(1)).delete(post);
        assertEquals(post, deletedPost);
    }

    @Test
    void deletePost_shouldThrowPostException_whenInvalidPostId() {

        int postId = 1;
        when(postRepo.findById(postId)).thenReturn(Optional.empty());


        assertThrows(PostException.class, () -> userService.deletePost(postId));
    }
	


    //***************************************************************************
    
    
    @BeforeEach
    void setUp4() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePost() throws PostException {
        int postId = 1;
        Post post = new Post();
        post.setId(postId);
        post.setLikes(5);

        when(postRepo.findById(postId)).thenReturn(Optional.of(post));
        when(postRepo.save(post)).thenReturn(post);

        int result = userService.likePost(postId);

        assertEquals(6, result);
    }

    @Test
    void testDisLikePost() throws PostException {
        int postId = 1;
        Post post = new Post();
        post.setId(postId);
        post.setLikes(5);

        when(postRepo.findById(postId)).thenReturn(Optional.of(post));
        when(postRepo.save(post)).thenReturn(post);

        int result = userService.disLikePost(postId);

        assertEquals(4, result);
    }

    @Test
    void testLikePostInvalidPostId() {
        int postId = 1;

        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> {
            userService.likePost(postId);
        });
    }

    @Test
    void testDisLikePostInvalidPostId() {
        int postId = 1;

        when(postRepo.findById(postId)).thenReturn(Optional.empty());

        assertThrows(PostException.class, () -> {
            userService.disLikePost(postId);
        });
    }

    
    //******************************************************************
    
    
    @Test
	public void testGetTotalNumberOfPosts() throws PostException {

		List<Post> posts = new ArrayList<>();
		posts.add(new Post());
		posts.add(new Post());
		when(postRepo.findAll()).thenReturn(posts);


		Integer result = userService.getTotalNumberOfPosts();


		assertEquals(2, result);
	}

	@Test
	public void testGetTotalNumberOfPosts_NoPostsFound() throws PostException {

		List<Post> posts = new ArrayList<>();
		when(postRepo.findAll()).thenReturn(posts);


		PostException exception = assertThrows(PostException.class, () -> {
			userService.getTotalNumberOfPosts();
		});

		assertEquals("No posts found", exception.getMessage());
	}

    
	//*****************************************************************
    
    
	@Test
	public void testGetTop5MostLikedPost() throws PostException {

		Post post1 = new Post();
		post1.setId(1);
		post1.setContent("Post 1");
		post1.setLikes(10);
		
		Post post2 = new Post();
		post2.setId(2);
		post2.setContent("Post 2");
		post2.setLikes(20);
		
		Post post3 = new Post();
		post3.setId(3);
		post3.setContent("Post 3");
		post3.setLikes(30);
		
		List<Post> posts = new ArrayList<>();
		posts.add(post1);
		posts.add(post2);
		posts.add(post3);
		

		when(postRepo.findAll()).thenReturn(posts);
		

		List<Post> result = userService.getTop5MostLikedPost();
		

		assertEquals(3, result.size());
		assertEquals(post3.getId(), result.get(0).getId());
		assertEquals(post2.getId(), result.get(1).getId());
		assertEquals(post1.getId(), result.get(2).getId());
	}
	
	@Test
	public void testGetTop5MostLikedPost_NoPostsFound() {

		when(postRepo.findAll()).thenReturn(new ArrayList<>());
		
		assertThrows(PostException.class, () -> userService.getTop5MostLikedPost(), "No posts found");
	}

}
