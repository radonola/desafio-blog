package br.com.raissa.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDto {
	
	@JsonProperty(value = "post_id")
	@NotNull(message = "Campo post_id é obrigatório")
	private Long postId;

	@NotBlank(message = "Campo texto é obrigatório")
	private String texto;
	
}
