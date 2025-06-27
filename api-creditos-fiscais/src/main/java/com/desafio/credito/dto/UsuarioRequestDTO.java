package com.desafio.credito.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO de requisição de Usuário",
    example = "{\"nome\":\"João da Silva\",\"login\":\"joaosilva\",\"senha\":\"123456\"}")
public class UsuarioRequestDTO {
  @Schema(description = "Nome do usuário", example = "João da Silva")
  @NotBlank(message = "Nome é obrigatório")
  private String nome;

  @Schema(description = "Login do usuário", example = "joaosilva")
  @NotBlank(message = "Login é obrigatório")
  private String login;

  @Schema(description = "Senha do usuário", example = "123456")
  @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
  private String senha;
}
