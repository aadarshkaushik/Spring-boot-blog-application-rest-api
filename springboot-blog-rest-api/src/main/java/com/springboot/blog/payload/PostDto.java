package com.springboot.blog.payload;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "PostDto Model Information")
public class PostDto{
	
	private Long id;
	
	//title should not be empty or null
	//title should have atleast 2 characters
	@Schema(description = "Blog Post Title")
	@NotEmpty
	@Size(min = 2, message = "Post title should be at least 2 characters" )
	private String title;
	
	//post description should not be empty or null
	//post description should have atleast 10 characters
	@Schema(description = "Blog Post Description")
	@NotEmpty
	@Size(min = 10, message = "Post description should have at least 10 characters")
	private String description;
	
	@Schema(description = "Blog Post Content")
	@NotEmpty
	private String content;
	
	@Schema(description = "Blog Post Comments")
	private Set<CommentDto> comments;
	
	@Schema(description = "Blog Post Category")
	private long categoryId;
}