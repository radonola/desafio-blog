package br.com.raissa.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginRequestDto {

	@NotBlank(message = "Campo email é obrigatório")
	private String email;
	
	@NotBlank(message = "Campo senha é obrigatório")
	private String senha;

}
