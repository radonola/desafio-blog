package br.com.raissa.blog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.raissa.blog.exception.ComentarioException;
import br.com.raissa.blog.model.Comentario;
import br.com.raissa.blog.repository.ComentarioRepository;

@Service
public class ComentarioService {
	
	private static final String ERRO_REQUISICAO = "Erro na requisição";
	private static final String ERRO_SERVIDOR = "Erro no processamento da requisição";

	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public List<Comentario> findComentariosByPostId(Comentario comentario) {
		
		return comentarioRepository.findByPostId(comentario.getPost().getId());
	}
	
	public Comentario createComentario(Comentario comentario) {
		
		try {
			
			return comentarioRepository.save(comentario);
		} catch (Exception e) {
			
			throw new ComentarioException(e.getLocalizedMessage(), ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void removeComentarioByUserEmailAndComentarioId(Comentario comentario) {
		
		try {
			
			Optional<Comentario> comentarioOptional = comentarioRepository.findByIdAndUsuarioEmail(comentario.getId(), comentario.getUsuario().getEmail());
			
			if(comentarioOptional.isPresent()) {
				
				comentarioRepository.deleteById(comentarioOptional.get().getId());
			} else {
				
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			
			throw new ComentarioException(String.format("Não existe um comentário com id %d e usuário de nome %s para ser deletado.", comentario.getId(), comentario.getUsuario().getEmail()),
					ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			throw new ComentarioException(e.getLocalizedMessage(), ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
