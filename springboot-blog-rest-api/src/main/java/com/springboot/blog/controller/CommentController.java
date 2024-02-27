package com.springboot.blog.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(
			@PathVariable(value = "postId") long postId,
			@RequestBody CommentDto commentDto
			){
		return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
	}
	@GetMapping("/posts/{postid}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable(value = "postid") Long postId){
		return commentService.getCommentsByPostId(postId);
	}
	@GetMapping("/posts/{postid}/comments/{id}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "postid") Long postId, 
			                                         @PathVariable(value = "id") Long commentId) throws BlogAPIException{
		CommentDto commentDto = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<>(commentDto, HttpStatus.OK);
	}
}