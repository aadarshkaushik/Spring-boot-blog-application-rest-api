package com.springboot.blog.service.implementation;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImplementation implements PostService {
	@Autowired
	private PostRepository postRepository;
	private ModelMapper modelMapper;
	private CategoryRepository categoryRepository;

	public PostServiceImplementation(PostRepository postRepository, ModelMapper modelMapper,
			CategoryRepository categoryRepository) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
		.orElseThrow(()->
		new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		
		Post post = mapToEntity(postDto);
		post.setCategory(category);
		Post savedPost = postRepository.save(post);
		
		PostDto postResponse = mapToDto(savedPost);
		
		return postResponse;
	}

	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDirection) {
		
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
				:Sort.by(sortBy).descending();
		
		//creating pageable instrance
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		//get content for the page object
		List<Post> listOfPosts = posts.getContent();
		
		List<PostDto> content = listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(content);
		postResponse.setPageNo(posts.getNumber());
		postResponse.setPageSize(posts.getSize());
		postResponse.setTotalElements(posts.getTotalElements());
		postResponse.setTotalPages(posts.getTotalPages());
		postResponse.setLast(posts.isLast());
		
		return postResponse;
	}
	
	//CONVERTING ENTITY INTO DTO
	private PostDto mapToDto(Post post) {
		
		PostDto postDto = modelMapper.map(post, PostDto.class);
		return postDto;
	}
	
	//CONVERTING DTO INTO ENTITY
	private Post mapToEntity(PostDto postDto) {
		
		Post post = modelMapper.map(postDto, Post.class);
		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Post","id",id));
		
		Category category = categoryRepository.findById(postDto.getCategoryId())
				.orElseThrow(()-> 
				new ResourceNotFoundException("Category","id",postDto.getCategoryId()));
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		post.setCategory(category);
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException("Post","id",id));
		postRepository.delete(post);
	}

	@Override
	public List<PostDto> getPostByCategoryId(Long categoryId) {
		
		Category category = categoryRepository.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
		
		List<Post> posts = postRepository.findByCategoryId(categoryId);
		
		return posts.stream().map((post) -> mapToDto(post)).collect(Collectors.toList());
	}
}