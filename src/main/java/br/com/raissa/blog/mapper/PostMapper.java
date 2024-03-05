package br.com.raissa.blog.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.raissa.blog.dto.PostWithIdDto;
import br.com.raissa.blog.exception.PostException;
import br.com.raissa.blog.form.PostFormData;
import br.com.raissa.blog.model.Post;
import br.com.raissa.blog.model.Usuario;
import br.com.raissa.blog.service.UsuarioService;
import br.com.raissa.blog.util.ImageUtil;

@Component
public class PostMapper {
	
	private static final String ERRO_SERVIDOR = "Erro no processamento da requisição";	
	
	@Autowired
	private UsuarioService usuarioService;

	public Post toPostWithFormDataAndEmailUser(PostFormData postFormData, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);

		byte[] imageCompressed = null;
		
		try {
			
			imageCompressed = ImageUtil.compressImage(postFormData.getImage().getBytes());
		} catch (Exception e) {
			
			throw new PostException(String.format("Não foi possível comprimir a imagem. Mensagem: %s", e.getLocalizedMessage()),
        			ERRO_SERVIDOR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return Post.builder()
				.texto(postFormData.getTexto())
				.imageData(imageCompressed)
				.usuario(usuario)
			.build();
	}
	
	public Post toPostWithIdAndEmailUser(Long id, String email) {
		
		Usuario usuario = usuarioService.getUsuarioByUsername(email);
		
		return Post.builder().id(id).usuario(usuario).build();
	}
	
	public PostWithIdDto toPostWithIdDto(Post post) {
		
		byte[] image = ImageUtil.decompressImage(post.getImageData());
		
		return PostWithIdDto.builder().id(post.getId()).texto(post.getTexto()).imagem(image).build();
	}
	
	public List<PostWithIdDto> toPostWithIdDtoBatch(List<Post> posts) {
		
		return posts.stream().map(this::toPostWithIdDto).toList();
	}
	
}
