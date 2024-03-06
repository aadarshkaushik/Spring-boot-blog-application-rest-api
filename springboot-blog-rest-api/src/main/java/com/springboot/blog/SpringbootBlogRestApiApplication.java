package com.springboot.blog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",
				description = "Spring Boot Blog App REST APIs Documentation",
				version = "1.0",
				contact = @Contact(
						name = "Aadarsh Kaushik",
						email= "aadarshkaushik654@gmail.com",
						url = "https://www.linkedin.com/in/aadarsh-kaushik-404370194/"
						),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.linkedin.com/in/aadarsh-kaushik-404370194/"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Documentation",
				url = "https://github.com/aadarshkaushik/Spring-boot-blog-application-rest-api"
				)
		)
public class SpringbootBlogRestApiApplication {
    @Bean
    ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
}