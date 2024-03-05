package br.com.raissa.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raissa.blog.dto.PostDto;
import br.com.raissa.blog.dto.PostWithIdDto;
import br.com.raissa.blog.mapper.PostMapper;
import br.com.raissa.blog.security.JWTUtil;
import br.com.raissa.blog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private PostMapper postMapper;

	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<List<PostWithIdDto>> getAllPosts() {
		
		return ResponseEntity.status(HttpStatus.OK).body(postMapper.toPostWithIdDtoBatch(postService.getAllPosts()));
	}
	
	@PostMapping
	public ResponseEntity<PostWithIdDto> createPost(@Valid @RequestBody PostDto postDto, HttpServletRequest httpServletRequest) {
		
		String email = getEmailFromJwt(httpServletRequest);
		
		PostWithIdDto responsePostDto = postMapper.toPostWithIdDto(
				postService.createPost(postMapper.toPostWithEmailUser(postDto, email)));
		
		return ResponseEntity.status(HttpStatus.OK).body(responsePostDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		
		String email = getEmailFromJwt(httpServletRequest);
		
		postService.removePostByUserEmailAndPostId(postMapper.toPostWithIdAndEmailUser(id, email));				
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	private String getEmailFromJwt(HttpServletRequest httpServletRequest) {
		
		String token = jwtUtil.getJWTFromRequest(httpServletRequest);
		
		if(StringUtils.hasText(token) && jwtUtil.validateToken(token)) {
			
			return jwtUtil.getUsernameFromJWT(token);
		}
		
		return null;
	}
}
