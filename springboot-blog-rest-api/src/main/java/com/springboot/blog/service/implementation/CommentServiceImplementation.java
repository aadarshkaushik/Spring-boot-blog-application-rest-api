package com.springboot.blog.service.implementation;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImplementation implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;

	public CommentServiceImplementation(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}
	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {
		Comment comment = mapToEntity(commentDto);
		
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(
				()-> new ResourceNotFoundException("Post","id",postId));
		
		//set post to comment entity
		comment.setPost(post);
		
		//comment entity to db
		Comment savedComment = commentRepository.save(comment);
		return mapToDto(savedComment);
	}
	//Converting entity to dto
	private CommentDto mapToDto(Comment comment) {
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setName(comment.getName());
		commentDto.setEmail(comment.getEmail());
		commentDto.setBody(comment.getBody());
		return commentDto;
	}
	//Converting dto to entity
	private Comment mapToEntity(CommentDto commentDto) {
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setName(commentDto.getName());
		comment.setEmail(commentDto.getEmail());
		comment.setBody(commentDto.getBody());
		return comment;
	}
	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
	
		//retreive comments by post id
		List<Comment> comments = commentRepository.findByPostId(postId);
		
		//convert list of comment entities to list of comment dto's
		return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
	}
	@Override
	public CommentDto getCommentById(Long postId, Long commentId) throws BlogAPIException {
		//retrieve post entity by id
		Post post = postRepository.findById(postId).orElseThrow(
						()-> new ResourceNotFoundException("Post","id",postId));
		
		//retrieve comment by id
		Comment comment = commentRepository.findById(commentId).orElseThrow(
				() -> new ResourceNotFoundException("Comment", "id",commentId));
		
		if(!comment.getPost().getId().equals(post.getId())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Comment does not belong to post");
		}
		
		return mapToDto(comment);
	}
}