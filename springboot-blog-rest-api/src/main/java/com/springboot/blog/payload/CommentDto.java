package com.springboot.blog.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Comment Model Information")
public class CommentDto {
	private long id;
	
	//name should not be null or empty
	@Schema(description = "Blog Post Comment Name")
	@NotEmpty(message = "Name shouldn't be null or empty")
	private String name;
	
	//name should not be null or empty
	//email field verification
	@Schema(description = "Blog Post Comment Email")
	@NotEmpty(message = "Email should not be null or empty")
	@Email
	private String email;
	
	//Comment body should not be null or empty
	//Comment body must be minimum 10 characters
	@Schema(description = "Blog Post Comment Body")
	@NotEmpty
	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String body;
}