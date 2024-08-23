package com.ip.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class UserPostServiceImpl implements UserPostService {
	
	@Autowired
	private UserRepo uRepo;
	
	@Autowired
	private PostRepo pRepo;
	

	public UserPostServiceImpl(UserRepo userRepo) {
		this.uRepo = userRepo;
	}
	


	



	







	@Override
	public User createUser(UserDTO dto) throws UserException {
		
		Optional<User> op =  uRepo.findByEmail(dto.getEmail());
		
		if(op.isPresent()) {
			throw new UserException("User with email: " + dto.getEmail() + " already exists");
		}
		
		
		User user = new User();
		
		user.setName(dto.getName());
		user.setEmail(dto.getEmail());
		user.setBio(dto.getBio());
		
		return uRepo.save(user);
		
	}

	
	@Override
	public User getUser(Integer userId) throws UserException {
		
		Optional<User> op = uRepo.findById(userId);
		
		if(op.isEmpty()) {
			throw new UserException("Invalid user id");
		}
		
		return op.get();
	}

	
	
	@Override
	public User updateUser(Integer userId, UserDTOV1 dto) throws UserException {
		
		Optional<User> op = uRepo.findById(userId);
		
		if(op.isEmpty()) {
			throw new UserException("Invalid user id");
		}
		
		User user = op.get();
		
		if(dto.getName()== null && dto.getBio() == null) {
			throw new UserException("Both name and bio can not be null");
		}
		
		if(dto.getName() != null) {
			user.setName(dto.getName());
		}
		
		if(dto.getBio() != null) {
			user.setBio(dto.getBio());
		}
		
		user.setUpdatedAt(LocalDateTime.now());
		
		return uRepo.save(user);
	}

	
	
	@Override
	public User deleteUser(Integer userId) throws UserException {
		
		Optional<User> op = uRepo.findById(userId);
		
		if(op.isEmpty()) {
			throw new UserException("Invalid user id");
		}
		
		uRepo.delete(op.get());
		
		return op.get();
	}

	
	
	@Override
	public Integer getTotalNumberOfUsers() throws UserException {
		
		List<User> users =  uRepo.findAll();
		
		if(users.isEmpty()) {
			throw new UserException("No user found");
		}
		
		return users.size();
	}

	
	
	@Override
	public List<User> getTop5ActiveUsers() throws UserException {
		
		List<User> users =  uRepo.findAll();
		
		if(users.isEmpty()) {
			throw new UserException("No user found");
		}
		
		List<User> top5Users =  users.stream()
									 .sorted((u1, u2) -> Integer.compare(u2.getPosts().size(), u1.getPosts().size()))
									 .limit(5)
									 .collect(Collectors.toList());
		
		return top5Users;
	}


	
	
	@Override
	public Post createPost(PostDTO dto) throws UserException {
		
		Optional<User> op = uRepo.findById(dto.getUserId());
		
		if(op.isEmpty()) {
			throw new UserException("Invalid user id");
		}
		
		User user = op.get();
		
		Post post = new Post();
		
		post.setContent(dto.getContent());
		post.setLikes(0);
		
		post.setUser(user);
		user.getPosts().add(post);
		
		return pRepo.save(post);
		
	}



	
	@Override
	public Post getPost(Integer postId) throws PostException {
		
		Optional<Post> op = pRepo.findById(postId);
		
		if(op.isEmpty()) {
			throw new PostException("Invalid post id");
		}
		
		return op.get();
	}


	
	
	@Override
	public Post updatePost(PostDTOV1 dto) throws PostException {
		
		Optional<Post> op = pRepo.findById(dto.getPostId());
		
		if(op.isEmpty()) {
			throw new PostException("Invalid post id");
		}
		
		Post post = op.get();
		
		if(dto.getContent() == null) {
			throw new PostException("Post content can not be nu");
		}
		
		post.setContent(dto.getContent());
		post.setUpdated_at(LocalDateTime.now());
		
		
		return pRepo.save(post);
	}


	
	
	@Override
	public Post deletePost(Integer postId) throws PostException {
		
		Optional<Post> op = pRepo.findById(postId);
		
		if(op.isEmpty()) {
			throw new PostException("Invalid post id");
		}
		
		Post post = op.get();
		
		pRepo.delete(post);
		
		return post;
	}


	
	
	@Override
	public Integer likePost(Integer postId) throws PostException {
		
		Optional<Post> op = pRepo.findById(postId);
		
		if(op.isEmpty()) {
			throw new PostException("Invalid post id");
		}
		
		Post post = op.get();
		
		post.setLikes(post.getLikes() + 1);
		
		return pRepo.save(post).getLikes();
		
	}


	
	
	@Override
	public Integer disLikePost(Integer postId) throws PostException {
		
		Optional<Post> op = pRepo.findById(postId);
		
		if(op.isEmpty()) {
			throw new PostException("Invalid post id");
		}
		
		Post post = op.get();
		
		post.setLikes(post.getLikes() - 1);
		
		return pRepo.save(post).getLikes();
	}


	
	
	@Override
	public Integer getTotalNumberOfPosts() throws PostException {
		
		List<Post> posts =  pRepo.findAll();
		
		if(posts.isEmpty()) {
			throw new PostException("No posts found");
		}
		
		return posts.size();
	}


	
	
	@Override
	public List<Post> getTop5MostLikedPost() throws PostException {
		
		List<Post> posts =  pRepo.findAll();
		
		if(posts.isEmpty()) {
			throw new PostException("No posts found");
		}
		
		List<Post> top5Posts =  posts.stream()
									 .sorted((u1, u2) -> Integer.compare(u2.getLikes(), u1.getLikes()))
									 .limit(5)
									 .collect(Collectors.toList());
		
		return top5Posts;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}
