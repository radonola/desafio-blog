package br.com.raissa.blog.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.raissa.blog.dto.ComentarioDto;
import br.com.raissa.blog.dto.ComentarioResponseDto;
import br.com.raissa.blog.model.Comentario;
import br.com.raissa.blog.model.Post;
import br.com.raissa.blog.model.Usuario;
import br.com.raissa.blog.service.PostService;
import br.com.raissa.blog.service.UsuarioService;

@Component
public class ComentarioMapper {
	
	@Autowired
	private UsuarioService usuarioService;	
	
	@Autowired
	private PostService postService;
	
	public Comentario toComentarioWithPostId(Long id) {
		
		return Comentario.builder()
					.post(Post.builder().id(id).build())
				.build();
	}
	
	public Comentario toComentarioWithEmailUser(ComentarioDto comentarioDto, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);
		
		Post post = postService.getPostById(Post.builder().id(comentarioDto.getPostId()).build());
		
		return Comentario.builder().texto(comentarioDto.getTexto()).usuario(usuario).post(post).build();
	}	
	
	public Comentario toComentarioWithIdAndEmailUser(Long id, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);
		
		return Comentario.builder().id(id).usuario(usuario).build();
	}		
		
	public ComentarioResponseDto toComentarioResponseDto(Comentario comentario) {
		
		return ComentarioResponseDto.builder()
					.email(comentario.getUsuario().getEmail())
					.texto(comentario.getTexto())
				.build();
	}
	
	public List<ComentarioResponseDto> toComentarioResponseDtoBatch(List<Comentario> comentarios) {
		
		return comentarios.stream().map(this::toComentarioResponseDto).toList();
	}	
	
}
