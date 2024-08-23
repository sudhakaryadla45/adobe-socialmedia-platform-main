package com.ip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ip.dto.PostDTO;
import com.ip.dto.PostDTOV1;
import com.ip.dto.UserDTO;
import com.ip.dto.UserDTOV1;
import com.ip.exception.PostException;
import com.ip.exception.UserException;
import com.ip.model.Post;
import com.ip.model.User;
import com.ip.service.UserPostService;

@RestController
@CrossOrigin(origins = "*") 
public class UserPostController {
	
	@Autowired
	private UserPostService upService;
	
	@PostMapping("/users")
	public ResponseEntity<User> createUserHandler(@Validated @RequestBody UserDTO dto) throws UserException {
		return new ResponseEntity<>(upService.createUser(dto), HttpStatus.CREATED);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUserHandler(@PathVariable("id") Integer userId) throws UserException {
		return new ResponseEntity<>(upService.getUser(userId), HttpStatus.OK);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUserHandler(@PathVariable("id") Integer userId, @Validated @RequestBody UserDTOV1 dto) throws UserException {
		return new ResponseEntity<>(upService.updateUser(userId, dto), HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<User> deleteUserHandler(@PathVariable("id") Integer userId) throws UserException {
		return new ResponseEntity<>(upService.deleteUser(userId), HttpStatus.OK);
	}
	
	@GetMapping("/analytics/users")
	public ResponseEntity<Integer> getTotalNumberOfUsersHandler() throws UserException {
		return new ResponseEntity<>(upService.getTotalNumberOfUsers(), HttpStatus.OK);
	}
	
	
	@GetMapping("/analytics/users/top-active")
	public ResponseEntity<List<User>> getTop5ActiveUsersHandler() throws UserException{
		return new ResponseEntity<>(upService.getTop5ActiveUsers(), HttpStatus.OK);
	}

	@PostMapping("/posts")
	public ResponseEntity<Post> createPostHandler(@Validated @RequestBody PostDTO dto) throws UserException{
		return new ResponseEntity<>(upService.createPost(dto), HttpStatus.CREATED);
	}
	
	@GetMapping("/posts/{id}")
	public ResponseEntity<Post> getPostHandler(@PathVariable("id") Integer postId) throws PostException {
		return new ResponseEntity<>(upService.getPost(postId), HttpStatus.OK);
	}
	
	@PostMapping("/posts/update")
	public ResponseEntity<Post> updatePostHandler(@Validated @RequestBody PostDTOV1 dto) throws PostException {
		return new ResponseEntity<>(upService.updatePost(dto), HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<Post> deletePostHandler(@PathVariable("id") Integer postId) throws PostException {
		return new ResponseEntity<>(upService.deletePost(postId), HttpStatus.OK);
	}
	
	@PostMapping("/posts/{id}/like")
	public ResponseEntity<Integer> likePostHandler(@PathVariable("id") Integer postId) throws PostException {
		return new ResponseEntity<>(upService.likePost(postId), HttpStatus.OK);
	}
	
	@PostMapping("/posts/{id}/unlike")
	public ResponseEntity<Integer> disLikePostHandler(@PathVariable("id") Integer postId) throws PostException{
		return new ResponseEntity<>(upService.disLikePost(postId), HttpStatus.OK);
	}
	
	
	@GetMapping("/analytics/posts")
	public ResponseEntity<Integer> getTotalNumberOfPosts() throws PostException {
		return new ResponseEntity<>(upService.getTotalNumberOfPosts(), HttpStatus.OK);
	}
	
	@GetMapping("/analytics/posts/top-liked")
	public ResponseEntity<List<Post>> getTop5MostLikedPost() throws PostException{
		return new ResponseEntity<>(upService.getTop5MostLikedPost(), HttpStatus.OK);
	}

}
