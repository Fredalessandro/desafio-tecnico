package com.desafio.credito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO de requisição de login")
public class LoginRequestDTO {
  @Schema(description = "Login do usuário", example = "joaosilva")
  private String login;
  @Schema(description = "Senha do usuário", example = "123456")
  private String senha;
}
