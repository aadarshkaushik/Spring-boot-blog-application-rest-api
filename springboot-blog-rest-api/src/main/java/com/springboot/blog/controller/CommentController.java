package com.springboot.blog.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@Tag(name = "CRUD REST APIs for Comment Resource")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}
	//create comment rest api
	@Operation(
			summary = "Create Comment REST API",
			description = "Create Comment REST API is for posting user comments to posts."
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201, Comment Created"
			)
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@PathVariable(value = "postId") long postId,
			@Valid
			@RequestBody CommentDto commentDto
			){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	//get comment rest api
	@Operation(
			summary = "Get Comments by Post Id REST API",
			description = "Get Comments REST API is used for retrieve comments by provided post id."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Fetched"
			)
	@GetMapping("/posts/{postid}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postid") Long postId){
		return commentService.getCommentsByPostId(postId);
	}
	//get comments by comment id rest api
	@Operation(
			summary = "Get Comment by Comment Id REST API",
			description = "Get Comment  by Comment Id REST API is used for retrieving comment by provided comment id."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Fetched"
			)
	@GetMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postid") Long postId, 
			                                         @PathVariable(value = "id") Long commentId) throws BlogAPIException{
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
	@Operation(
			summary = "Update Comment REST API",
			description = "Update Comment REST API is used for update the comment against given comment id."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Updated"
			)
	@PutMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postid")Long postId,
			                                        @PathVariable(value = "id") Long commentId, 
			                                        @Valid
			                                        @RequestBody CommentDto commentRequest) throws BlogAPIException{
		CommentDto updatedComment = commentService.updateComment(postId, commentId, commentRequest);
		return new ResponseEntity<>(updatedComment, HttpStatus.OK);
	}
	@Operation(
			summary = "Delete Comment REST API",
			description = "Delete Comment REST API is used for delete the comment for given comment-id"
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200, Deleted"
			)
	@DeleteMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "postid")Long postId, 
			                                    @PathVariable(value = "id")Long commentId) throws BlogAPIException{
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
	}
}