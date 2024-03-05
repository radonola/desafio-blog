package br.com.raissa.blog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.raissa.blog.exception.AuthException;
import br.com.raissa.blog.model.Role;
import br.com.raissa.blog.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RoleService {
	
	private static final String ERRO_REQUISICAO = "Erro na requisição";
	private static final String ERRO_SERVIDOR = "Erro no processamento da requisição";
	
	@Autowired
	private RoleRepository roleRepository;

	public Role getRoleByNome(String nome) {
		
		try {
			
			Optional<Role> roleOptional = roleRepository.findByNome(nome);
			
			if(roleOptional.isPresent()) {
				
				return roleOptional.get();
			} else {
				
				throw new EntityNotFoundException();
			}
		} catch (EntityNotFoundException e) {
			
			throw new AuthException(String.format("Role %s não existe.", nome), 
					ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			throw new AuthException(e.getLocalizedMessage(), ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
