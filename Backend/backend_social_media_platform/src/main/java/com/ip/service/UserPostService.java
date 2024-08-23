package com.ip.service;

import java.util.List;

import com.ip.dto.PostDTO;
import com.ip.dto.PostDTOV1;
import com.ip.dto.UserDTO;
import com.ip.dto.UserDTOV1;
import com.ip.exception.PostException;
import com.ip.exception.UserException;
import com.ip.model.Post;
import com.ip.model.User;

public interface UserPostService {
	
	public User createUser(UserDTO dto) throws UserException;
	
	
	public User getUser(Integer userId) throws UserException;
	
	
	public User updateUser(Integer userId, UserDTOV1 dto) throws UserException;
	
	
	public User deleteUser(Integer userId) throws UserException;
	
	
	public Integer getTotalNumberOfUsers() throws UserException;
	
	
	public List<User> getTop5ActiveUsers() throws UserException;

	
	public Post createPost(PostDTO dto) throws UserException;
	
	
	public Post getPost(Integer postId) throws PostException;
	
	
	public Post updatePost(PostDTOV1 dto) throws PostException;
	
	
	public Post deletePost(Integer postId) throws PostException;
	
	
	public Integer likePost(Integer postId) throws PostException;
	
	
	public Integer disLikePost(Integer postId) throws PostException;
	
	
	public Integer getTotalNumberOfPosts() throws PostException;
	
	
	public List<Post> getTop5MostLikedPost() throws PostException;
	
}
