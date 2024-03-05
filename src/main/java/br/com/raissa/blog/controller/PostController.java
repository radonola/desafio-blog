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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.raissa.blog.dto.PostWithIdDto;
import br.com.raissa.blog.exception.PostException;
import br.com.raissa.blog.form.PostFormData;
import br.com.raissa.blog.mapper.PostMapper;
import br.com.raissa.blog.security.JWTUtil;
import br.com.raissa.blog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private static final String ERRO_REQUISICAO = "Erro na requisição";	
	private static final String VALIDATION_MESSAGE_PARAM = "Parâmetros 'texto' ou 'imagem' não preenchidos no form-data";

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
	public ResponseEntity<PostWithIdDto> createPost(@Valid @RequestParam(required = false) String texto, @RequestParam(required = false) MultipartFile imagem, HttpServletRequest httpServletRequest) {
		
		if(texto == null && imagem == null) throw new PostException(VALIDATION_MESSAGE_PARAM, ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		
		String email = getEmailFromJwt(httpServletRequest);
		
		PostFormData formData = PostFormData.builder().texto(texto).image(imagem).build();
		
		PostWithIdDto responsePostDto = postMapper.toPostWithIdDto(
				postService.createPost(postMapper.toPostWithFormDataAndEmailUser(formData, email)));
		
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
