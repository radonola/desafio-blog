package br.com.raissa.blog.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.raissa.blog.dto.AuthLoginRequestDto;
import br.com.raissa.blog.dto.AuthLoginResponseDto;
import br.com.raissa.blog.dto.AuthRegisterRequestDto;
import br.com.raissa.blog.dto.AuthRegisterResponseDto;
import br.com.raissa.blog.model.Role;
import br.com.raissa.blog.model.Usuario;

@Component
public class AuthMapper {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Usuario toUsuario(AuthLoginRequestDto authLoginRequestDto) {
		
		return Usuario.builder()
				.email(authLoginRequestDto.getEmail())
				.senha(authLoginRequestDto.getSenha())
				.build();
	}

	public Usuario toUsuarioWithEncryptedPassword(AuthRegisterRequestDto authRegisterRequestDto) {
		
		return Usuario.builder()
				.email(authRegisterRequestDto.getEmail())
				.nomeCompleto(authRegisterRequestDto.getNomeCompleto())
				.senha(passwordEncoder.encode(authRegisterRequestDto.getSenha()))
				.build();
	}
	
	public AuthRegisterResponseDto toAuthRegisterResponseDto(Usuario usuario) {
		
		return AuthRegisterResponseDto.builder()
			.id(usuario.getId())
			.username(usuario.getEmail())
			.roles(usuario.getRoles().stream().map(Role::getNome).toList())
			.build();
	}
	
	public AuthLoginResponseDto toAuthLoginResponseDto(String token) {
		
		return AuthLoginResponseDto.builder()
			.accessToken(token)
			.build();
	}
	
}
