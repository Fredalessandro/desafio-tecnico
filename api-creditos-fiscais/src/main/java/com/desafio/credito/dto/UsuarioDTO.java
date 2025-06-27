package com.desafio.credito.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO de resposta de Usuário",
    example = "{\"id\":1,\"nome\":\"João da Silva\",\"login\":\"joaosilva\"}")
public class UsuarioDTO {
  @Schema(description = "ID do usuário", example = "1")
  private Long id;

  @Schema(description = "Nome do usuário", example = "João da Silva")
  private String nome;

  @Schema(description = "Login do usuário", example = "joaosilva")
  private String login;
}
