package com.desafio.credito.controller;

import com.desafio.credito.entity.Usuario;
import com.desafio.credito.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.desafio.credito.dto.UsuarioRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerIT {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    usuarioRepository.deleteAll();
  }

  @Test
  void deveCadastrarELogarUsuario() throws Exception {
    UsuarioRequestDTO usuario = new UsuarioRequestDTO("Maria Teste", "mariateste", "senha123");
    // Cadastro
    mockMvc
        .perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isOk()).andExpect(jsonPath("$.login", is("mariateste")))
        .andExpect(jsonPath("$.senha").doesNotExist());
    // Login
    mockMvc
        .perform(post("/usuarios/login").contentType(MediaType.APPLICATION_JSON)
            .content("{\"login\":\"mariateste\",\"senha\":\"senha123\"}"))
        .andExpect(status().isOk()).andExpect(jsonPath("$.token", not(emptyString())));
  }

  @Test
  void loginDeveFalharComSenhaErrada() throws Exception {
    Usuario usuario =
        Usuario.builder().nome("Maria Teste").login("mariateste").senha("senha123").build();
    usuarioRepository.save(usuario);
    mockMvc
        .perform(post("/usuarios/login").contentType(MediaType.APPLICATION_JSON)
            .content("{\"login\":\"mariateste\",\"senha\":\"errada\"}"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void cadastroDeveFalharComSenhaCurta() throws Exception {
    UsuarioRequestDTO usuario = new UsuarioRequestDTO("Maria Teste", "mariateste", "123");
    mockMvc
        .perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("Senha deve ter pelo menos 6 caracteres")));
  }

  @Test
  void cadastroDeveFalharComLoginVazio() throws Exception {
    UsuarioRequestDTO usuario = new UsuarioRequestDTO("Maria Teste", "", "senha123");
    mockMvc
        .perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("Login é obrigatório")));
  }

  @Test
  void cadastroDeveFalharComNomeVazio() throws Exception {
    UsuarioRequestDTO usuario = new UsuarioRequestDTO("", "mariateste", "senha123");
    mockMvc
        .perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", containsString("Nome é obrigatório")));
  }

  @Test
  void cadastroDeveFalharComLoginJaExistente() throws Exception {
    UsuarioRequestDTO usuario = new UsuarioRequestDTO("Maria Teste", "mariateste", "senha123");
    // Primeiro cadastro
    mockMvc.perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(usuario))).andExpect(status().isOk());
    // Segundo cadastro com mesmo login
    mockMvc
        .perform(post("/usuarios").contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(usuario)))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.message", containsString("Login já está em uso")));
  }
}
