package com.desafio.credito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO de resposta de login")
public class LoginResponseDTO {
  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;
  @Schema(description = "Login do usuário", example = "joaosilva")
  private String login;
  @Schema(description = "Token JWT gerado", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
  private String token;
}
