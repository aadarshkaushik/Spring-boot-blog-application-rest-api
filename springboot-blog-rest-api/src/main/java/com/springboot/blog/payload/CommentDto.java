package com.springboot.blog.payload;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
	private long id;
	
	//name should not be null or empty
	@NotEmpty(message = "Name shouldn't be null or empty")
	private String name;
	
	//name should not be null or empty
	//email field verification
	@NotEmpty(message = "Email should not be null or empty")
	@Email
	private String email;
	
	//Comment body should not be null or empty
	//Comment body must be minimum 10 characters
	@NotEmpty
	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String body;
}