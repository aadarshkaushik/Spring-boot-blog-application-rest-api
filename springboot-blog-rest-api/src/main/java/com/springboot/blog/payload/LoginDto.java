package com.springboot.blog.payload;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Login Model Information")
public class LoginDto {
	@Schema(description = "Username or Email for Login")
	private String usernameOrEmail;
	@Schema(description = "User Password for Login")
	private String password;
}