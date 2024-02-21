package com.springboot.blog.service.implementation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImplementation implements PostService {
	@Autowired
	private PostRepository postRepository;
	
	public PostServiceImplementation(PostRepository postRepository) {
		super();
		this.postRepository = postRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		
		Post post = mapToEntity(postDto);
		
		Post savedPost = postRepository.save(post);
		
		PostDto postResponse = mapToDto(savedPost);
		
		return postResponse;
	}

	@Override
	public List<PostDto> getAllPosts(int pageNo, int pageSize) {
		
		//creating pageable instrance
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		
		Page<Post> posts = postRepository.findAll(pageable);
		
		//get content for the page object
		List<Post> listOfPosts = posts.getContent();
		return listOfPosts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
	}
	
	//CONVERTING ENTITY INTO DTO
	private PostDto mapToDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		
		return postDto;
	}
	
	//CONVERTING DTO INTO ENTITY
	private Post mapToEntity(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		return post;
	}

	@Override
	public PostDto getPostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		return mapToDto(post);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		Post updatedPost = postRepository.save(post);
		return mapToDto(updatedPost);
	}

	@Override
	public void deletePostById(long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
		postRepository.delete(post);
	}
}