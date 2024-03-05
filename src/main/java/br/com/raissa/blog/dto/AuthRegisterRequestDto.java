package br.com.raissa.blog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRegisterRequestDto {

	@NotBlank(message = "Campo email é obrigatório")
	@Size(max = 100, message = "Limite de caracteres email: 100")
	@Email(message = "O campo deve ser preenchido com um e-mail válido")
	private String email;
	
	@NotBlank(message = "Campo nome_completo é obrigatório")
	@JsonProperty(value = "nome_completo")
	private String nomeCompleto;
	
	@NotBlank(message = "Campo senha é obrigatório")
	@Size(max = 100, message = "Limite de caracteres senha: 100")
	private String senha;

}
