package com.springboot.blog.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "CRUD REST APIs for Autentication Resource")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	
	//build login rest api
	@Operation(
			summary = "Login REST API",
			description = "Login REST API is used for user login."
			)
	@ApiResponse(
			responseCode = "200",
			description = "Http Status 200 Login Successful"
			)
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
		String token = authenticationService.login(loginDto);
		
		JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
	
	//build register rest-api
	@Operation(
			summary = "Registration REST API",
			description = "Login REST API is used for user registration."
			)
	@ApiResponse(
			responseCode = "201",
			description = "Http Status 201, Registration Successful"
			)
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws BlogAPIException{
		String response = authenticationService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
}