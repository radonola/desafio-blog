package br.com.raissa.blog.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.raissa.blog.dto.PostDto;
import br.com.raissa.blog.dto.PostWithIdDto;
import br.com.raissa.blog.model.Post;
import br.com.raissa.blog.model.Usuario;
import br.com.raissa.blog.service.UsuarioService;

@Component
public class PostMapper {
	
	@Autowired
	private UsuarioService usuarioService;

	public Post toPost(PostDto postDto) {
		
		return Post.builder().texto(postDto.getTexto()).build();
	}
	
	public Post toPostWithEmailUser(PostDto postDto, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);
		
		return Post.builder().texto(postDto.getTexto()).usuario(usuario).build();
	}
	
	public Post toPostWithIdAndEmailUser(Long id, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);
		
		return Post.builder().id(id).usuario(usuario).build();
	}
	
	public PostWithIdDto toPostWithIdDto(Post post) {
		
		return PostWithIdDto.builder().id(post.getId()).texto(post.getTexto()).build();
	}
	
	public List<PostWithIdDto> toPostWithIdDtoBatch(List<Post> posts) {
		
		return posts.stream().map(this::toPostWithIdDto).toList();
	}
	
}
