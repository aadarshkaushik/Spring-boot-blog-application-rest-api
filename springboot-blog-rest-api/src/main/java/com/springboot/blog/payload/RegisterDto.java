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
@Schema(description = "Blog Registraiton Model Information")
public class RegisterDto {
	@Schema(description = "Name")
	private String name;
	@Schema(description = "Username")
	private String username;
	@Schema(description = "Email")
	private String email;
	@Schema(description = "Password")
	private String password;
}