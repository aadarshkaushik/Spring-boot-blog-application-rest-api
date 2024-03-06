package com.springboot.blog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
		name = "Bear Authentication",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
public class SecurityConfig {
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf
        		.disable()).authorizeHttpRequests((authorize) ->
                authorize
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults())
        .exceptionHandling(exception -> 
        exception.authenticationEntryPoint(authenticationEntryPoint)).sessionManagement
        (session -> 
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	/*
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails aadarsh = User.builder()
				.username("aadarsh")
				.password(passwordEncoder().encode("aadarsh"))
				.roles("USER")
				.build();
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();
		UserDetails moderator = User.builder()
				.username("moderator")
				.password(passwordEncoder().encode("moderator"))
				.roles("MODERATOR")
				.build();
		return new InMemoryUserDetailsManager(aadarsh,admin,moderator);
		}
	 */
}