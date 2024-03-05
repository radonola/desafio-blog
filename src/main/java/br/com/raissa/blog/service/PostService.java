package br.com.raissa.blog.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.raissa.blog.exception.PostException;
import br.com.raissa.blog.model.Post;
import br.com.raissa.blog.repository.PostRepository;

@Service
public class PostService {
	
	private static final String ERRO_REQUISICAO = "Erro na requisição";
	private static final String ERRO_SERVIDOR = "Erro no processamento da requisição";

	@Autowired
	private PostRepository postRepository;
	
	public List<Post> getAllPosts() {
		
		return postRepository.findAll();
	}
	
	public Post getPostById(Post post) {
		
		return postRepository.findById(post.getId()).orElseThrow(() -> new PostException(
				String.format("Não existe o post com id %d", post.getId()),
				ERRO_REQUISICAO, HttpStatus.BAD_REQUEST));
	}
	
	public Post createPost(Post post) {
		
		try {
			
			return postRepository.save(post);
		} catch (Exception e) {
			
			throw new PostException(e.getLocalizedMessage(), ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public void removePostByUserEmailAndPostId(Post post) {
		
		try {
			
			Optional<Post> postOptional = postRepository.findByIdAndUsuarioEmail(post.getId(), post.getUsuario().getEmail());
			
			if(postOptional.isPresent()) {
				
				postRepository.deleteById(postOptional.get().getId());
			} else {
				
				throw new NoSuchElementException();
			}
		} catch (NoSuchElementException e) {
			
			throw new PostException(String.format("Não existe um post com id %d e usuário de nome %s para ser deletado.", post.getId(), post.getUsuario().getEmail()),
					ERRO_REQUISICAO, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			
			throw new PostException(e.getLocalizedMessage(), ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
