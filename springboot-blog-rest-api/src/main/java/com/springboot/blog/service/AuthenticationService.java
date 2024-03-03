package com.springboot.blog.service;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;

public interface AuthenticationService {
	String login(LoginDto loginDto);
	String register(RegisterDto registerDto) throws BlogAPIException;
}