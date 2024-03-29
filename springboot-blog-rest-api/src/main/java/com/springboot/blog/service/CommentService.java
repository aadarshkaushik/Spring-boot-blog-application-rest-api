package com.springboot.blog.service;
import java.util.List;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(long postId, CommentDto commentDto);
	List<CommentDto> getCommentsByPostId(long postId);
	CommentDto getCommentById(Long postId, Long commentId) throws BlogAPIException;
	CommentDto updateComment(Long postId, Long commentId, CommentDto commentRequest) throws BlogAPIException;
	void deleteComment(Long postId, Long commentId) throws BlogAPIException;
}