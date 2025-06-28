package com.desafio.credito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.desafio.credito.entity.Usuario;
import com.desafio.credito.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
class CreditoControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Test
  void buscarCreditosPorNfse_deveRetornarCreditosParaNfseExistente() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/api/creditos/7891011").contentType(MediaType.APPLICATION_JSON));
    result.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
        .andExpect(jsonPath("$[0].numeroNfse", is("7891011")));
  }

  @Test
  void buscarCreditosPorNfse_deveRetornar404ParaNfseInexistente() throws Exception {
    mockMvc.perform(get("/api/creditos/0000000").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void buscarCreditoPorNumero_deveRetornarCreditoParaNumeroExistente() throws Exception {
    mockMvc.perform(get("/api/creditos/credito/123456").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(jsonPath("$.numeroCredito", is("123456")))
        .andExpect(jsonPath("$.numeroNfse", is("7891011")));
  }

  @Test
  void buscarCreditoPorNumero_deveRetornar404ParaNumeroInexistente() throws Exception {
    mockMvc.perform(get("/api/creditos/credito/999999").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  void status_deveRetornarOk() throws Exception {
    mockMvc.perform(get("/api/creditos/status")).andExpect(status().isOk())
        .andExpect(content().string(containsString("API de Créditos funcionando!")));
  }

  private String obterTokenValido() throws Exception {
    // Cria usuário e obtém token
    Usuario usuario = Usuario.builder().nome("JWT Teste").login("jwtuser").senha("jwtpass").build();
    usuarioRepository.save(usuario);
    String response = mockMvc
        .perform(post("/usuarios/login").contentType(MediaType.APPLICATION_JSON)
            .content("{\"login\":\"jwtuser\",\"senha\":\"jwtpass\"}"))
        .andReturn().getResponse().getContentAsString();
    String token = objectMapper.readValue(response, Map.class).get("token").toString();
    return "Bearer " + token;
  }

  @Test
  void buscarCreditosPorNfse_deveRetornar401SemToken() throws Exception {
    mockMvc.perform(get("/api/creditos/7891011")).andExpect(status().isUnauthorized());
  }

  @Test
  void buscarCreditosPorNfse_deveRetornar200ComToken() throws Exception {
    String token = obterTokenValido();
    mockMvc.perform(get("/api/creditos/7891011").header("Authorization", token))
        .andExpect(status().isOk());
  }
}
