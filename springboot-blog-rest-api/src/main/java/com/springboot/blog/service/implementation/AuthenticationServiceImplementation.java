package com.springboot.blog.service.implementation;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthenticationService;

@Service
public class AuthenticationServiceImplementation implements AuthenticationService {
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;

	public AuthenticationServiceImplementation(AuthenticationManager authenticationManager,
			UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDto.getUsernameOrEmail(),loginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return "User Logged-in successfully!";
	}

	@Override
	public String register(RegisterDto registerDto) throws BlogAPIException {
		//add check for username existing in database
		if(userRepository.existsByUsername(registerDto.getUsername())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username already exists");
		}
		//add check for email existing in database
		if(userRepository.existsByEmail(registerDto.getEmail())) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Email already exists");
		}
		
		//setting role for new user as "ROLE_USER"
		Set<Role> roles = new HashSet<>();
		Role userRoles = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRoles);
		
		//setting user details through requested object
		User user = new User();
		user.setName(registerDto.getName());
		user.setUsername(registerDto.getUsername());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		user.setRoles(roles);
		
		userRepository.save(user);
		
		return "User registered successfully!..";
	}
}