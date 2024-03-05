package br.com.raissa.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raissa.blog.dto.AuthLoginRequestDto;
import br.com.raissa.blog.dto.AuthLoginResponseDto;
import br.com.raissa.blog.dto.AuthRegisterRequestDto;
import br.com.raissa.blog.dto.AuthRegisterResponseDto;
import br.com.raissa.blog.mapper.AuthMapper;
import br.com.raissa.blog.service.AuthService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthMapper authMapper;
	
	@PostMapping(value = "/register")
	public ResponseEntity<AuthRegisterResponseDto> registerUser(@RequestBody @Valid AuthRegisterRequestDto authRegisterRequestDto) {
		
		AuthRegisterResponseDto authRegisterResponseDto = authMapper.toAuthRegisterResponseDto(
				authService.registerUsuario(authMapper.toUsuarioWithEncryptedPassword(authRegisterRequestDto)));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(authRegisterResponseDto);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<AuthLoginResponseDto> loginUser(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDto) {
		
		AuthLoginResponseDto authLoginResponseDto = authMapper.toAuthLoginResponseDto(
				authService.aunthenticateUsuario(authMapper.toUsuario(authLoginRequestDto)));
		
		return ResponseEntity.status(HttpStatus.OK).body(authLoginResponseDto);
	}
	
}
