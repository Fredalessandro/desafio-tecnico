package com.desafio.credito.controller;

import com.desafio.credito.dto.*;
import com.desafio.credito.service.UsuarioService;
import com.desafio.credito.config.JwtUtil;
import com.desafio.credito.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


import java.util.List;

@Tag(name = "Usuários", description = "Operações de cadastro, autenticação e consulta de usuários")
@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
  private final UsuarioService usuarioService;
  private final JwtUtil jwtUtil;
  private final UsuarioMapper usuarioMapper;

  @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário no sistema.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json",
              schema = @io.swagger.v3.oas.annotations.media.Schema(
                  implementation = UsuarioRequestDTO.class),
              examples = {@io.swagger.v3.oas.annotations.media.ExampleObject(
                  name = "Exemplo de cadastro",
                  value = "{\"nome\":\"João da Silva\",\"login\":\"joaosilva\",\"senha\":\"123456\"}")})),
      responses = {
          @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso",
              content = @io.swagger.v3.oas.annotations.media.Content(
                  schema = @io.swagger.v3.oas.annotations.media.Schema(
                      implementation = UsuarioDTO.class),
                  examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                      value = "{\"id\":1,\"nome\":\"João da Silva\",\"login\":\"joaosilva\"}"))),
          @ApiResponse(responseCode = "400", description = "Erro de validação"),
          @ApiResponse(responseCode = "409", description = "Login já está em uso")})
  @PostMapping
  public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
    UsuarioDTO salvo = usuarioService.salvar(usuarioRequestDTO);
    return ResponseEntity.ok(salvo);
  }

  @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados.",
      responses = @ApiResponse(responseCode = "200", description = "Lista de usuários",
          content = @io.swagger.v3.oas.annotations.media.Content(
              schema = @io.swagger.v3.oas.annotations.media.Schema(
                  implementation = UsuarioDTO.class, type = "array"),
              examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                  value = "[{\"id\":1,\"nome\":\"João da Silva\",\"login\":\"joaosilva\"}]"))))
  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> listar() {
    List<UsuarioDTO> usuarios =
        usuarioService.listarTodos().stream().map(usuarioMapper::toDTO).toList();
    return ResponseEntity.ok(usuarios);
  }

  @Operation(summary = "Buscar usuário por ID", description = "Busca um usuário pelo seu ID.",
      responses = {
          @ApiResponse(responseCode = "200", description = "Usuário encontrado",
              content = @io.swagger.v3.oas.annotations.media.Content(
                  schema = @io.swagger.v3.oas.annotations.media.Schema(
                      implementation = UsuarioDTO.class),
                  examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                      value = "{\"id\":1,\"nome\":\"João da Silva\",\"login\":\"joaosilva\"}"))),
          @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Long id) {
    return usuarioService.buscarPorId(id).map(usuarioMapper::toDTO).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo seu ID.",
      security = @SecurityRequirement(name = "bearerAuth"))
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletar(@PathVariable Long id) {
    usuarioService.deletar(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Login do usuário",
      description = "Autentica o usuário e retorna um token JWT.",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
          content = @io.swagger.v3.oas.annotations.media.Content(
              schema = @io.swagger.v3.oas.annotations.media.Schema(
                  implementation = LoginRequestDTO.class),
              examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                  value = "{\"login\":\"joaosilva\",\"senha\":\"123456\"}"))),
      responses = {@ApiResponse(responseCode = "200", description = "Login bem-sucedido",
          content = @io.swagger.v3.oas.annotations.media.Content(
              schema = @io.swagger.v3.oas.annotations.media.Schema(
                  implementation = LoginResponseDTO.class),
              examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                  value = "{\"nome\":\"João da Silva\",\"login\":\"joaosilva\",\"token\":\"eyJhbGciOiJIUzI1NiIsInR5cCI6...\"}"))),
          @ApiResponse(responseCode = "401", description = "Credenciais inválidas")})
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
    String login = loginRequest.getLogin();
    String senha = loginRequest.getSenha();
    return usuarioService.autenticar(login, senha)
        .map(usuario -> ResponseEntity.ok(
            new LoginResponseDTO(usuario.getNome(), usuario.getLogin(), jwtUtil.gerarToken(login))))
        .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
  }
}
