package com.springboot.blog.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for Post Resource")
public class PostController {
	
	@Autowired
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	//create post rest-api
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@Operation(
			summary = "Create Post REST API",
			description = "Create Post REST API is used to save the into database"
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201 Created"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/createpost")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	//display all post rest-api
	@Operation(
			summary = "Display All Post REST API",
			description = "Display All REST API is used to display all the post records from database"
			)
	@GetMapping("/showallpost")
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection
			){
		return postService.getAllPosts(pageNo,pageSize,sortBy,sortDirection);
	}
	//display post by rest-api
	@Operation(
			summary = "Get Post by Post-Id REST API",
			description = "Get Post by Post-Id REST API is used to retrieve post by post id from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Fetched"
			)
	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") long id){
		return ResponseEntity.ok(postService.getPostById(id));
	}
	//update post rest-api
	@Operation(summary = "Update Post REST API",
			description = "Update Post REST API is used to update post into database"
			)
	@ApiResponse(
			responseCode = "200",description = "Http Status 200 Successfully Updated"
			)
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
		PostDto postResponse = postService.updatePost(postDto, id);
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}
	//delete post rest-api
	@Operation(
			summary = "Delete Post by Post-Id REST API",
			description = "Delete Post by Post Id REST API is used to delete post from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Deleted Successfully"
			)
	@SecurityRequirement(
			name = "Bear Authentication"
			)
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
		postService.deletePostById(id);
		return new ResponseEntity<>("Post entity deleted successfully", HttpStatus.OK);
	}
	
	//build get post by category id
	@Operation(
			summary = "Get Post by Category-Id REST API",
			description = "Get Post by Category Id REST API is used to retrieve post by category id from database"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Successfully Fetched"
			)
	@GetMapping("/getpostbycategory/{id}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId){
		List<PostDto> postDtos = postService.getPostByCategoryId(categoryId);
		return ResponseEntity.ok(postDtos);
	}
}