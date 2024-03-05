package br.com.raissa.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.raissa.blog.exception.AuthException;
import br.com.raissa.blog.model.Usuario;
import br.com.raissa.blog.security.JWTUtil;

@Service
public class AuthService {
	
	private static final String REGISTER_DEFAULT_ROLE = "USER";
	private static final String ERRO_REQUISICAO = "Erro na requisição";

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public Usuario registerUsuario(Usuario usuario) {
		
		usuario.setRole(roleService.getRoleByNome(REGISTER_DEFAULT_ROLE));
		
		return usuarioService.addUsuario(usuario);
	}
	
	public String aunthenticateUsuario(Usuario usuario) {
		
		Authentication authentication = null;
		
		try {
			
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha()));
		} catch (Exception e) {

			throw new AuthException("Email ou senha incorreto.", ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		}
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return jwtUtil.generateToken(authentication);
	}
	
}
