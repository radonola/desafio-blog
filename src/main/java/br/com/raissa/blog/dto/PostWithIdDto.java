package br.com.raissa.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostWithIdDto {

	private Long id;
	
	private String texto;
	
	private byte[] imagem;
	
}
