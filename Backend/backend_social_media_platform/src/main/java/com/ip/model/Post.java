package com.ip.model;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@NotNull(message = "Post content can't be null")
	@NotBlank(message = "Post content can't be blank")
	@NotEmpty (message = "Post content can't be empty")
	@Size(min = 1, max = 300, message = "Post content should have characters in the range 1 - 300")
	private String content;
	
	private LocalDateTime created_at = LocalDateTime.now();
		
	private LocalDateTime updated_at;
	
	@Min(value = 0, message = "Likes should not be negative")
	private Integer likes;
	
	
	@ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "user_id")
	private User user;


}
