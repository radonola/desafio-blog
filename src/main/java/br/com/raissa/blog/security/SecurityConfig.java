package br.com.raissa.blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.raissa.blog.security.filter.JWTAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private static final String[] AUTH_WHITELIST = {
			"/v3/api-docs/**",
	        "/swagger-ui/**",
	        "/swagger-ui/",
	        "/swagger-ui"};
	
	@Bean
	SecurityFilterChain customSecurityFilterChain(HttpSecurity http, JWTAuthEntryPoint jwtAuthEntryPoint) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
			.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
			.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(AUTH_WHITELIST).permitAll()
				.requestMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
			)
			.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
				
		return http.build();
	}
	
	@Bean
    JWTAuthenticationFilter jwtAuthenticationFilter() {
		
        return new JWTAuthenticationFilter();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
