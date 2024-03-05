package br.com.raissa.blog.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.raissa.blog.model.Role;
import br.com.raissa.blog.model.Usuario;
import br.com.raissa.blog.service.UsuarioService;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final String PREFIX_ROLE = "ROLE_";
	
	private static final String ROLE_USER = "USER";
	private static final String ROLE_ADMIN = "ADMIN";
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(username);
		
		authorityToRoleConverter(usuario);
		
		return new User(usuario.getEmail(), usuario.getSenha(), mapRolesToAuthorities(usuario.getRoles()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNome())).collect(Collectors.toList());
	}
	
	private void authorityToRoleConverter(Usuario usuario) {
		
		for(Role role : usuario.getRoles()) {
			
			if(role.getNome().equalsIgnoreCase(ROLE_USER) || role.getNome().equalsIgnoreCase(ROLE_ADMIN)) {
				
				role.setNome(PREFIX_ROLE.concat(role.getNome()));
			}
		}
	}
	
}
