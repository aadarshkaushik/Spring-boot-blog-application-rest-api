package com.springboot.blog.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
	
	private AuthenticationService authenticationService;

	public AuthenticationController(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}
	//build login rest api
	@PostMapping(value = {"/login","/signin"})
	public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
		String response = authenticationService.login(loginDto);
		return ResponseEntity.ok(response);
	}
	//build register rest-api
	@PostMapping(value = {"/register","/signup"})
	public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws BlogAPIException{
		String response = authenticationService.register(registerDto);
		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
}