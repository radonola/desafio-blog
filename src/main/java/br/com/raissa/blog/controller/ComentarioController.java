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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.raissa.blog.dto.ComentarioDto;
import br.com.raissa.blog.dto.ComentarioResponseDto;
import br.com.raissa.blog.exception.ComentarioException;
import br.com.raissa.blog.mapper.ComentarioMapper;
import br.com.raissa.blog.security.JWTUtil;
import br.com.raissa.blog.service.ComentarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
	
	private static final String ERRO_REQUISICAO = "Erro na requisição";	
	private static final String VALIDATION_MESSAGE_PARAM = "Parâmetro 'id_post' da requisição é obrigatório";

	@Autowired
	private ComentarioService comentarioService;
	
	@Autowired
	private ComentarioMapper comentarioMapper;

	@Autowired
	private JWTUtil jwtUtil;
	
	@GetMapping
	public ResponseEntity<List<ComentarioResponseDto>> getComentariosByPost(@RequestParam(required = false, value = "id_post") Long idPost) {
		
		if(idPost == null) throw new ComentarioException(VALIDATION_MESSAGE_PARAM, ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		
		List<ComentarioResponseDto> comentarioResponseDtoList = comentarioMapper.toComentarioResponseDtoBatch(
				comentarioService.findComentariosByPostId(comentarioMapper.toComentarioWithPostId(idPost)));
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarioResponseDtoList);
	}
	
	@PostMapping
	public ResponseEntity<ComentarioResponseDto> createComentario(@Valid @RequestBody ComentarioDto comentarioDto, HttpServletRequest httpServletRequest) {
		
		String email = getEmailFromJwt(httpServletRequest);
		
		ComentarioResponseDto comentarioResponseDto = comentarioMapper.toComentarioResponseDto(
				comentarioService.createComentario(comentarioMapper.toComentarioWithEmailUser(comentarioDto, email)));
		
		return ResponseEntity.status(HttpStatus.OK).body(comentarioResponseDto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest httpServletRequest) {
		
		String email = getEmailFromJwt(httpServletRequest);
		
		comentarioService.removeComentarioByUserEmailAndComentarioId(comentarioMapper.toComentarioWithIdAndEmailUser(id, email));				
		
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
