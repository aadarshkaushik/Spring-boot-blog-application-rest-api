package com.springboot.blog.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Category Model Information")
public class CategoryDto {
	private Long id;
	@Schema(description = "Blog Post Category Name")
	private String name;
	@Schema(description = "Blog Post Category Description")
	private String description;
}